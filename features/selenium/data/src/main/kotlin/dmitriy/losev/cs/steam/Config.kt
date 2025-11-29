package dmitriy.losev.cs.steam

import java.nio.file.Path
import java.nio.file.Paths

data class SteamAccount(
    val username: String,
    val password: String
)

data class Config(
    val steamPath: Path = Paths.get("C:\\Program Files (x86)\\Steam\\steam.exe"),
    val cs2Path: Path = Paths.get("E:\\SteamLibrary\\steamapps\\common\\Counter-Strike Global Offensive"),
    val accounts: List<SteamAccount> = emptyList(),
    val loginWaitTimeout: Long = 60_000, // 2 минуты на логин
    val cs2LaunchTimeout: Long = 60_000, // 3 минуты на запуск CS2
    val betweenLaunchDelay: Long = 5_000, // 5 секунд между запусками
    val launchCS2: Boolean = true, // Запускать ли CS2
    val skipCS2WaitOnFirstInstance: Boolean = false // Пропустить ожидание CS2 для первого экземпляра (для тестирования)
)
