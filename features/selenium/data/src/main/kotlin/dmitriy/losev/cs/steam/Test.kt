package dmitriy.losev.cs.steam

import java.nio.file.Paths
import mu.KotlinLogging
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

fun main() {

    ConsoleEncodingFix.fix()

    println("═".repeat(80))
    println("STEAM MULTI-INSTANCE SEQUENTIAL LAUNCHER")
    println("═".repeat(80))
    println()

    // Проверка прав администратора
    if (!isRunningAsAdmin()) {
        logger.error { "❌ Требуются права администратора!" }
        println("\nПерезапустите программу от имени администратора")
        println("Нажмите Enter для выхода...")
        readLine()
        exitProcess(1)
    }

    logger.info { "✓ Запущено с правами администратора" }

    // Режим выбора
    println("Выберите режим:")
    println("1. Запуск нескольких Steam клиентов")
    println("2. Диагностика процессов Steam")
    print("\nВаш выбор (1/2): ")

    val choice = readLine()?.trim()

    when (choice) {
        "2" -> {
            runDiagnostics()
            return
        }
        "1", "" -> {
            // Продолжаем с обычным режимом
        }
        else -> {
            println("Неверный выбор")
            exitProcess(1)
        }
    }

    // Конфигурация
    val config = loadConfig()

    if (config.accounts.isEmpty()) {
        logger.error { "❌ Не указаны аккаунты Steam!" }
        println("\nОтредактируйте файл config.txt и добавьте аккаунты")
        println("Нажмите Enter для выхода...")
        readLine()
        exitProcess(1)
    }

    println("\n📋 Конфигурация:")
    println("  Steam: ${config.steamPath}")
    println("  CS2: ${config.cs2Path}")
    println("  Аккаунтов: ${config.accounts.size}")
    println("  Запускать CS2: ${config.launchCS2}")
    println()

    // Создаем лаунчер
    val launcher = SteamSequentialLauncher(config)

    // Подготовка
    println("⏳ Подготовка к запуску...")
    launcher.prepare()

    // Запуск всех аккаунтов последовательно
    println("\n🚀 Начинаем последовательный запуск Steam клиентов...\n")

    try {
        launcher.launchAll()

        println("\n" + "═".repeat(80))
        println("✓ ВСЕ ЭКЗЕМПЛЯРЫ STEAM УСПЕШНО ЗАПУЩЕНЫ!")
        println("═".repeat(80))
        println("\n💡 Советы:")
        println("  - Не закрывайте эту программу, пока Steam клиенты работают")
        println("  - Для остановки всех клиентов нажмите Ctrl+C")
        println()

        // Ждем сигнала для завершения
        println("Нажмите Enter для остановки всех клиентов и выхода...")
        readLine()

        launcher.stopAll()

    } catch (e: Exception) {
        logger.error(e) { "❌ Ошибка при запуске: ${e.message}" }
        println("\nПроизошла ошибка. Проверьте логи.")
        launcher.stopAll()
    }

    println("\n✓ Завершено")
}

fun runDiagnostics() {
    println("\n═".repeat(80))
    println("РЕЖИМ ДИАГНОСТИКИ")
    println("═".repeat(80))
    println()

    val monitor = WindowsProcessMonitor()
    val service = SteamServiceManager()

    println("📊 Текущее состояние:")
    println()

    // Проверяем службу
    println("1. Steam Client Service:")
    // TODO: добавить проверку статуса службы
    println("   (проверьте вручную через services.msc)")
    println()

    // Показываем процессы
    println("2. Процессы Steam:")
    val allSteamProcesses = monitor.getAllSteamProcesses()
    if (allSteamProcesses.isEmpty()) {
        println("   ❌ Процессы Steam не найдены")
    } else {
        allSteamProcesses.forEach { proc ->
            println("   • ${proc.name} (PID: ${proc.pid})")
        }
    }
    println()

    // Тест запуска Steam
    println("3. Тест запуска Steam:")
    println("   Запускаем Steam...")

    service.stopSteamService()
    Thread.sleep(2000)
    monitor.killAllSteamProcesses()
    Thread.sleep(2000)

    val steamPath = Paths.get("C:\\Program Files (x86)\\Steam\\steam.exe")
    val processBuilder = ProcessBuilder(steamPath.toString(), "-silent").apply {
        directory(steamPath.parent.toFile())
    }

    val process = processBuilder.start()
    val initialPid = process.pid().toInt()

    println("   ✓ Steam запущен (PID: $initialPid)")
    println()
    println("   Мониторинг процессов в течение 60 секунд...")
    println("   (Авторизуйтесь в Steam, если нужно)")
    println()

    val startTime = System.currentTimeMillis()
    var lastProcesses = listOf<ProcessInfo>()

    repeat(60) { second ->
        Thread.sleep(1000)
        val currentProcesses = monitor.getAllSteamProcesses()

        if (currentProcesses != lastProcesses) {
            val elapsed = (System.currentTimeMillis() - startTime) / 1000
            println("   [$elapsed сек] Изменение процессов:")

            // Новые процессы
            val newProcesses = currentProcesses.filter { current ->
                lastProcesses.none { it.pid == current.pid }
            }
            newProcesses.forEach { proc ->
                println("     + ${proc.name} (PID: ${proc.pid})")
            }

            // Завершенные процессы
            val removedProcesses = lastProcesses.filter { last ->
                currentProcesses.none { it.pid == last.pid }
            }
            removedProcesses.forEach { proc ->
                println("     - ${proc.name} (PID: ${proc.pid})")
            }

            println()
            lastProcesses = currentProcesses
        }
    }

    println("\n✓ Диагностика завершена")
    println("\nОстановить Steam? (y/n): ")
    val stop = readLine()?.trim()?.lowercase()

    if (stop == "y" || stop == "yes") {
        monitor.killAllSteamProcesses()
        service.startSteamService()
        println("✓ Steam остановлен")
    }
}

fun loadConfig(): Config {
    // В реальном приложении загружаем из файла
    // Для примера используем тестовые данные

    println("📝 Введите данные аккаунтов:")
    val accounts = mutableListOf<SteamAccount>()

    while (true) {
        println("\nАккаунт #${accounts.size + 1} (Enter без ввода для завершения)")
        print("  Логин: ")
        val username = readLine()?.trim() ?: break
        if (username.isEmpty()) break

        print("  Пароль: ")
        val password = readLine()?.trim() ?: ""

        accounts.add(SteamAccount(username, password))

        print("\nДобавить еще аккаунт? (y/n): ")
        val more = readLine()?.trim()?.lowercase()
        if (more != "y" && more != "yes") break
    }

    // Опция запуска CS2
    print("\nЗапускать CS2 для каждого аккаунта? (y/n): ")
    val launchCS2 = readLine()?.trim()?.lowercase() == "y"

    return Config(
        accounts = accounts,
        launchCS2 = launchCS2,
        skipCS2WaitOnFirstInstance = false
    )
}