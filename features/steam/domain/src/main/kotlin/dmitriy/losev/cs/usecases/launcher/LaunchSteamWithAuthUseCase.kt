package dmitriy.losev.cs.usecases.launcher

//@Factory
//class LaunchSteamWithAuthUseCase(
//    private val getSteamGuardCodeFromFileUseCase: GetSteamGuardCodeFromFileUseCase,
//    private val createFileDirectoriesForUserUseCase: CreateFileDirectoriesForUserUseCase,
//    private val createSteamProcessUseCase: CreateSteamProcessUseCase,
//    private val authWithLoginAndPasswordUseCase: AuthWithLoginAndPasswordUseCase,
//    private val authWithSteamGuardCodeUseCase: AuthWithSteamGuardCodeUseCase,
//    private val closeSteamWindowUseCase: CloseSteamWindowUseCase
//): BaseUseCase {
//
//    suspend operator fun invoke(userName: String, password: String, index: Int): Result<Int> {
//        return createFileDirectoriesForUserUseCase.invoke(userName).mapCatching {
//            val steamProcessPid = createSteamProcessUseCase.invoke(userName, index).getOrThrow()
//            authWithLoginAndPasswordUseCase.invoke(pid = steamProcessPid, userName = userName, password = password, isFirst = true).getOrThrow()
//            val steamGuardCode = getSteamGuardCodeFromFileUseCase.invoke(userName).getOrThrow()
//            authWithSteamGuardCodeUseCase.invoke(pid = steamProcessPid, userName = userName, steamGuardCode = steamGuardCode).getOrThrow()
//            delay(3000L)
//            closeSteamWindowUseCase.invoke(pid = steamProcessPid).getOrThrow()
//            steamProcessPid.toInt()
//        }
//    }
//}