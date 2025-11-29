package dmitriy.losev.cs.steam.login

import com.sun.jna.Platform
import dmitriy.losev.cs.steam.ConsoleEncodingFix
import java.io.File

// Класс для запуска нескольких Steam с авто-логином
class MultiSteamLauncher {

    data class AccountConfig(
        val username: String,
        val password: String,
        val twoFactorCode: String? = null,
        val instanceName: String,
        val instancePath: String
    )

    fun launchSteamAndLogin(
        account: AccountConfig,
        steamPath: String = "C:\\Program Files (x86)\\Steam\\steam.exe"
    ) {
        println("\n" + "=".repeat(60))
        println("🚀 Запуск Steam для: ${account.username}")
        println("=".repeat(60))

        // Формируем команду запуска
        val command = buildList {
            add(steamPath)
            add("-master_ipc_name_override")
            add(account.instanceName)
            add("-windowed")
            add("-w")
            add("800")
            add("-h")
            add("600")
            add("-allowmultiple")
            add("-nofriendsui")
            add("-vgui")
            add("-noreactlogin")
        }

        // Настраиваем окружение для изоляции
        val processBuilder = ProcessBuilder(command)
        processBuilder.environment().apply {
            this["USERPROFILE"] = account.instancePath
            this["LOCALAPPDATA"] = "${account.instancePath}\\AppData\\Local"
            this["APPDATA"] = "${account.instancePath}\\AppData\\Roaming"
            this["TEMP"] = "${account.instancePath}\\Temp"
            this["TMP"] = "${account.instancePath}\\Temp"
        }

        // Создаём необходимые директории
        File(account.instancePath).apply {
            File(this, "AppData/Local").mkdirs()
            File(this, "AppData/Roaming").mkdirs()
            File(this, "Temp").mkdirs()
        }

        println("📂 Instance path: ${account.instancePath}")
        println("🔧 IPC name: ${account.instanceName}")

        // Запускаем Steam
        processBuilder.start()

        println("⏳ Ждём загрузки Steam (7 секунд)...")
        Thread.sleep(7000)

        // Автоматическая авторизация
        val autoLogin = SteamAutoLogin()
        autoLogin.login(
            username = account.username,
            password = account.password,
            twoFactorCode = account.twoFactorCode
        )

        println("✅ Готово для: ${account.username}")
    }
}

fun main() {

    ConsoleEncodingFix.fix()

    println("=".repeat(70))
    println("🎮 Steam Auto-Login Manager (Kotlin + JNA)")
    println("=".repeat(70))
    println()

    if (!Platform.isWindows()) {
        println("❌ Эта программа работает только на Windows!")
        return
    }

    // Конфигурация аккаунтов
    val accounts = listOf(
        MultiSteamLauncher.AccountConfig(
            username = "stor432myak",
            password = "B^SVLQkg.,9%_8i",
            twoFactorCode = "29MBG", // или "ABC12" если есть Steam Guard
            instanceName = "stor432myak",
            instancePath = "C:\\SteamInstances\\stor432myak"
        ),
        MultiSteamLauncher.AccountConfig(
            username = "steelcrow542",
            password = "PC\\=os3mE&TV=\\R",
            twoFactorCode = null,
            instanceName = "steelcrow542",
            instancePath = "C:\\SteamInstances\\steelcrow542"
        )
    )

    val launcher = MultiSteamLauncher()

    // Запускаем каждый аккаунт с задержкой
    accounts.forEachIndexed { index, account ->
        launcher.launchSteamAndLogin(account)

        // Задержка между запусками
        if (index < accounts.size - 1) {
            println("\n⏳ Ждём 5 секунд перед следующим аккаунтом...")
            Thread.sleep(5000)
        }
    }

    println("\n" + "=".repeat(70))
    println("✅ Все аккаунты запущены и авторизованы!")
    println("=".repeat(70))
}