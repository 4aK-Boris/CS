package dmitriy.losev.cs.repositories

//@Factory(binds = [SteamLauncherRepository::class])
//internal class SteamLauncherRepositoryImpl(
//    @Provided private val fileHandler: FileHandler,
//    private val steamLauncher: SteamLauncher
//) : SteamLauncherRepository {
//
//    override suspend fun createSteamProcess(userName: String, index: Int): Long {
//        return 0L//steamLauncher.createSteamProcess(userName, index)
//    }
//
//    override suspend fun createFileDirectoriesForUser(userName: String) {
//        fileHandler.createDirectories(mainDirectory = "remote/src/main/resources/steam/$userName", directories = listOf("AppData/Local", "AppData/Roaming", "Temp"))
//    }
//
//    override suspend fun authWithLoginAndPassword(pid: Long, userName: String, password: String, isFirst: Boolean) {
//        //steamLauncher.authWithLoginAndPassword(pid = pid, userName = userName, password = password, isFirst = isFirst)
//    }
//
//    override suspend fun authWithSteamGuardCode(pid: Long, userName: String, steamGuardCode: String) {
//        steamLauncher.authWithSteamGuardCode(pid = pid, userName = userName, steamGuardCode = steamGuardCode)
//    }
//
//    override suspend fun close(pid: Long) {
//        steamLauncher.closeSteamWindow(pid)
//    }
//}
