package dmitriy.losev.cs.steam

import java.nio.file.Paths

// Main.kt
import mu.KotlinLogging
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

fun main() {
    ConsoleEncodingFix.fix()

    println("\n" + "═".repeat(80))
    println("  ДИАГНОСТИКА STEAM HANDLES")
    println("═".repeat(80) + "\n")

    val serviceManager = WindowsServiceManager()
    val debugger = HandleDebugger()

    // Проверка прав
    if (!serviceManager.isRunningAsAdmin()) {
        println("❌ ТРЕБУЮТСЯ ПРАВА АДМИНИСТРАТОРА!")
        println("\nПерезапустите программу от имени администратора:")
        println("1. Правой кнопкой на программу")
        println("2. 'Запуск от имени администратора'\n")
        exitProcess(1)
    }

    println("✓ Права администратора подтверждены\n")

    // Останавливаем службу
    println("⏳ Останавка Steam Client Service...")
    serviceManager.stopSteamService()
    println("✓ Служба остановлена\n")

    // Закрываем все процессы Steam
    println("⏳ Закрытие существующих процессов Steam...")
    ProcessUtils.killProcessByName("steam.exe")
    ProcessUtils.killProcessByName("steamwebhelper.exe")
    Thread.sleep(3000)
    println("✓ Процессы закрыты\n")

    // Запускаем Steam
    println("⏳ Запуск Steam...")
    val steamPath = Paths.get("C:\\Program Files (x86)\\Steam\\steam.exe")

    if (!steamPath.toFile().exists()) {
        println("❌ Steam не найден по пути: $steamPath")
        println("   Укажите правильный путь к steam.exe")
        exitProcess(1)
    }

    val process = ProcessBuilder(
        steamPath.toString(),
        "-no-browser",
        "-silent"
    ).apply {
        directory(steamPath.parent.toFile())
    }.start()

    val initialPid = process.pid().toInt()
    println("✓ Steam запущен (PID: $initialPid)\n")

    // Анализ через разные интервалы времени
    val intervals = listOf(5, 10, 15, 20, 30)

    for ((index, seconds) in intervals.withIndex()) {
        println("\n" + "═".repeat(80))
        println("ПРОВЕРКА #${index + 1}: ПОСЛЕ $seconds СЕКУНД ОЖИДАНИЯ")
        println("═".repeat(80))

        if (index == 0) {
            println("⏳ Ожидание $seconds секунд...")
            Thread.sleep(seconds * 1000L)
        } else {
            val waitTime = seconds - intervals[index - 1]
            println("⏳ Дополнительное ожидание $waitTime секунд...")
            Thread.sleep(waitTime * 1000L)
        }

        // Находим все процессы Steam
        val steamProcesses = ProcessUtils.findProcessesByName("steam.exe")
        val steamProcessesWebHelper = ProcessUtils.findProcessesByName("steamwebhelper.exe")

        println("\n✓ Найдено процессов steam.exe: ${steamProcesses.size}")

        if (steamProcesses.isEmpty()) {
            println("⚠ Steam процессы не найдены! Возможно Steam закрылся.")
            break
        }

        steamProcesses.forEach { proc ->
            println("  - PID: ${proc.pid}, Parent PID: ${proc.parentPid}")
        }

        // Анализируем handles каждого процесса
        steamProcesses.forEach { proc ->
            debugger.analyzeProcessHandles(proc.pid)
        }

        // Анализируем handles каждого процесса
        steamProcessesWebHelper.forEach { proc ->
            debugger.analyzeProcessHandles(proc.pid)
        }

        // Спрашиваем продолжить ли
        if (seconds < 30) {
            println("\n" + "-".repeat(80))
            print("Продолжить ожидание? (Enter = да, q = выход): ")
            val input = readLine()?.trim()?.lowercase()

            if (input == "q" || input == "quit" || input == "exit") {
                println("Прерывание диагностики...")
                break
            }
        }
    }

    // Завершение
    println("\n" + "═".repeat(80))
    println("ДИАГНОСТИКА ЗАВЕРШЕНА")
    println("═".repeat(80))

    println("\nНажмите Enter для выхода...")
    readLine()

    // Очистка
    println("\n⏳ Завершение Steam...")
    process.destroy()
    Thread.sleep(2000)

    println("⏳ Восстановление Steam Client Service...")
    serviceManager.startSteamService()

    println("✓ Готово!\n")
}