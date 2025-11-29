package dmitriy.losev.cs

data class SteamConfig(
    val defaultSteamId: ULong = 0UL,
    val waitingTimeForSteamLoginAndPasswordWindow: Int = 20,
    val waitingTimeForSteamGuardCodeWindow: Int = 40,
    val waitingTimeForSteamWindows: Int = 20,
)
