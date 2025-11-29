package dmitriy.losev.cs.steam

import mu.KotlinLogging
import java.nio.file.Path
import kotlin.concurrent.thread

private val launcherLogger = KotlinLogging.logger("SteamSequentialLauncher")

class SteamSequentialLauncher(private val config: Config) {

    private val processMonitor = WindowsProcessMonitor()
    private val steamService = SteamServiceManager()
    private val launchedInstances = mutableListOf<LaunchedInstance>()

    data class LaunchedInstance(
        val account: SteamAccount,
        val steamPid: Int,
        val cs2Pid: Int?
    )

    fun prepare() {
        launcherLogger.info { "Остановка Steam Client Service..." }
        steamService.stopSteamService()
        Thread.sleep(2000)

        launcherLogger.info { "Закрытие всех процессов Steam..." }
        processMonitor.killAllSteamProcesses()
        Thread.sleep(2000)

        launcherLogger.info { "✓ Подготовка завершена" }
    }

    fun launchAll() {
        config.accounts.forEachIndexed { index, account ->
            val instanceNumber = index + 1

            println("─".repeat(80))
            println("🎮 ЭКЗЕМПЛЯР #$instanceNumber: ${account.username}")
            println("─".repeat(80))

            try {
                val instance = launchInstance(account, instanceNumber)
                launchedInstances.add(instance)

                println("✓ Экземпляр #$instanceNumber запущен успешно\n")

                // Задержка перед следующим запуском
                if (index < config.accounts.size - 1) {
                    println("⏳ Ожидание ${config.betweenLaunchDelay / 1000} сек перед следующим запуском...\n")
                    Thread.sleep(config.betweenLaunchDelay)
                }

            } catch (e: Exception) {
                launcherLogger.error(e) { "❌ Не удалось запустить экземпляр #$instanceNumber" }
                throw e
            }
        }
    }

    private fun launchInstance(account: SteamAccount, instanceNumber: Int): LaunchedInstance {
        // Шаг 1: Запускаем Steam
        println("  [1/5] Запуск Steam...")
        val initialPid = startSteam(account)
        println("        ✓ Steam запущен (PID: $initialPid)")

        // Шаг 2: Ждем перезапуска после логина
        println("  [2/5] Ожидание авторизации...")
        val newSteamPid = waitForLoginAndRestart(initialPid, account.username)
        println("        ✓ Авторизация успешна (PID: $newSteamPid)")

        var cs2Pid: Int? = null

        // Шаги 3-4: Запуск CS2 (опционально)
        if (config.launchCS2) {
            // Шаг 3: Запускаем CS2
            println("  [3/5] Запуск CS2...")
            launchCS2ForSteamInstance(newSteamPid)
            println("        ✓ CS2 запускается...")

            // Шаг 4: Ждем полной загрузки CS2
            println("  [4/5] Ожидание загрузки CS2...")

            // Для первого экземпляра можем пропустить ожидание (для тестирования)
            if (instanceNumber == 1 && config.skipCS2WaitOnFirstInstance) {
                println("        ⚠ Пропускаем ожидание CS2 (режим тестирования)")
                Thread.sleep(5000)
            } else {
                cs2Pid = waitForCS2FullyLoaded()
                println("        ✓ CS2 полностью загружена (PID: $cs2Pid)")
            }
        } else {
            println("  [3/5] Запуск CS2 пропущен (launchCS2 = false)")
            println("  [4/5] Ожидание CS2 пропущено")
            // Даем больше времени для стабилизации без CS2
            Thread.sleep(10000)
        }

        // Шаг 5: Небольшая задержка для стабилизации
        println("  [5/5] Стабилизация...")
        Thread.sleep(3000)
        println("        ✓ Готово")

        return LaunchedInstance(account, newSteamPid, cs2Pid)
    }

    private fun startSteam(account: SteamAccount): Int {
        val command = buildList {
            add(config.steamPath.toString())
            add("-silent")
            add("-no-browser")
            add("-nofriendsui")
            add("-tcp")
            // НЕ добавляем -login здесь, будем логиниться через UI
        }

        val processBuilder = ProcessBuilder(command).apply {
            directory(config.steamPath.parent.toFile())
        }

        val process = processBuilder.start()
        return process.pid().toInt()
    }

    private fun waitForLoginAndRestart(initialPid: Int, username: String): Int {
        val startTime = System.currentTimeMillis()

        println("        └─ Отслеживание процессов Steam во время логина...")
        println("        └─ Войдите в аккаунт '$username' в окне Steam")
        println()

        // Запоминаем начальные процессы
        val initialProcesses = processMonitor.getAllSteamProcesses()
        println("        └─ Начальные процессы Steam:")
        initialProcesses.forEach { proc ->
            println("           • ${proc.name} (PID: ${proc.pid})")
        }
        println()

        var loginDetected = false
        var newMainSteamPid: Int? = null
        var lastProcessCount = initialProcesses.size

        // Мониторим изменения в процессах
        while (!loginDetected) {
            if (System.currentTimeMillis() - startTime > config.loginWaitTimeout) {
                println("\n        ⚠ Таймаут ожидания. Текущие процессы:")
                processMonitor.getAllSteamProcesses().forEach { proc ->
                    println("           • ${proc.name} (PID: ${proc.pid})")
                }

                // Если исходный процесс все еще жив, возможно логин не завершен
                if (processMonitor.isProcessRunning(initialPid)) {
                    throw TimeoutException("Steam процесс (PID: $initialPid) все еще запущен. Возможно, логин не завершен или требуется Steam Guard.")
                } else {
                    // Процесс завершился, но мы не нашли новый
                    throw TimeoutException("Steam процесс завершился, но не перезапустился. Проверьте, не закрылся ли Steam.")
                }
            }

            Thread.sleep(1000)

            val currentProcesses = processMonitor.getAllSteamProcesses()

            // Метод 1: Исходный процесс завершился и появился новый
            if (!processMonitor.isProcessRunning(initialPid)) {
                println("        └─ ✓ Исходный процесс (PID: $initialPid) завершен")

                // Ждем немного для стабилизации
                Thread.sleep(3000)

                // Ищем новый главный процесс steam.exe
                val newSteamProcesses = processMonitor.findSteamProcesses()
                if (newSteamProcesses.isNotEmpty()) {
                    newMainSteamPid = newSteamProcesses.first().pid
                    println("        └─ ✓ Найден новый процесс Steam: $newMainSteamPid")
                    loginDetected = true
                    break
                }
            }

            // Метод 2: Появились новые дочерние процессы (steamwebhelper и т.д.)
            // Это часто происходит после успешного логина
            if (currentProcesses.size > lastProcessCount + 2) {
                println("        └─ ✓ Обнаружено увеличение процессов Steam (${lastProcessCount} → ${currentProcesses.size})")

                // Если исходный процесс жив, используем его
                if (processMonitor.isProcessRunning(initialPid)) {
                    newMainSteamPid = initialPid
                    println("        └─ ✓ Используем исходный процесс: $initialPid")
                } else {
                    // Иначе ищем главный steam.exe
                    val steamProcesses = processMonitor.findSteamProcesses()
                    if (steamProcesses.isNotEmpty()) {
                        newMainSteamPid = steamProcesses.first().pid
                        println("        └─ ✓ Найден главный процесс Steam: $newMainSteamPid")
                    }
                }

                loginDetected = true
                break
            }

            // Метод 3: Прошло достаточно времени и процессы стабильны
            val elapsed = (System.currentTimeMillis() - startTime) / 1000
            if (elapsed > 30 && currentProcesses.size >= 3) {
                println("        └─ ⚠ Используем резервный метод определения логина")

                if (processMonitor.isProcessRunning(initialPid)) {
                    newMainSteamPid = initialPid
                    println("        └─ ✓ Используем исходный процесс: $initialPid")
                } else {
                    val steamProcesses = processMonitor.findSteamProcesses()
                    if (steamProcesses.isNotEmpty()) {
                        newMainSteamPid = steamProcesses.first().pid
                        println("        └─ ✓ Найден процесс Steam: $newMainSteamPid")
                    }
                }

                loginDetected = true
                break
            }

            lastProcessCount = currentProcesses.size

            // Показываем прогресс каждые 10 секунд
            if (elapsed.toInt() % 10 == 0 && elapsed.toInt() > 0) {
                println("        └─ Ожидание... (${elapsed.toInt()} сек, процессов: ${currentProcesses.size})")
            }
        }

        if (newMainSteamPid == null) {
            throw Exception("Не удалось определить главный процесс Steam после логина")
        }

        println()
        println("        └─ ✓ Логин успешен! Главный PID: $newMainSteamPid")
        println()

        return newMainSteamPid
    }

    private fun launchCS2ForSteamInstance(steamPid: Int) {
        // Запускаем CS2 через Steam URL
        val cs2AppId = 730
        val steamUrl = "steam://run/$cs2AppId"

        val command = listOf(
            "cmd.exe",
            "/c",
            "start",
            "",
            steamUrl
        )

        ProcessBuilder(command).start()
    }

    private fun waitForCS2FullyLoaded(): Int {
        val startTime = System.currentTimeMillis()

        println("        └─ Поиск процесса cs2.exe...")

        var cs2Pid: Int? = null

        // Ждем появления процесса cs2.exe
        while (cs2Pid == null) {
            if (System.currentTimeMillis() - startTime > config.cs2LaunchTimeout) {
                throw TimeoutException("Таймаут запуска CS2 (${config.cs2LaunchTimeout / 1000} сек)")
            }

            val cs2Processes = processMonitor.findCS2Processes()
            if (cs2Processes.isNotEmpty()) {
                cs2Pid = cs2Processes.first().pid
                println("        └─ CS2 процесс найден: $cs2Pid")
            } else {
                Thread.sleep(1000)
            }
        }

        // Ждем пока CS2 загрузится (проверяем что процесс стабилен)
        println("        └─ Ожидание полной загрузки...")
        Thread.sleep(10000) // Даем 10 секунд на загрузку основных модулей

        // Проверяем что процесс все еще жив
        if (!processMonitor.isProcessRunning(cs2Pid)) {
            throw Exception("CS2 процесс завершился преждевременно")
        }

        return cs2Pid
    }

    fun stopAll() {
        println("\n⏳ Остановка всех экземпляров...")

        launchedInstances.forEach { instance ->
            try {
                processMonitor.killProcess(instance.steamPid)
                instance.cs2Pid?.let { processMonitor.killProcess(it) }
            } catch (e: Exception) {
                launcherLogger.warn { "Не удалось остановить процесс: ${e.message}" }
            }
        }

        Thread.sleep(2000)
        processMonitor.killAllSteamProcesses()

        println("⏳ Восстановление Steam Client Service...")
        steamService.startSteamService()

        launchedInstances.clear()
    }
}
