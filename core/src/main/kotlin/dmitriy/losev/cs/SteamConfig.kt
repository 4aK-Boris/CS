package dmitriy.losev.cs

data class SteamConfig(
    val defaultSteamId: Long = 0L,
    val waitingTimeForSteamLoginAndPasswordWindow: Int = 20,
    val waitingTimeForSteamGuardCodeWindow: Int = 40,
    val waitingTimeForSteamWindows: Int = 20,
)
