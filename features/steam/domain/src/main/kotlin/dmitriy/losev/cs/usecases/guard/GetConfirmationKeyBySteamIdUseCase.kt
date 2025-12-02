package dmitriy.losev.cs.usecases.guard

//@Factory
//class GetConfirmationKeyBySteamIdUseCase(
//    private val getSteamAccountUseCase: GetSteamAccountUseCase,
//    private val getConfirmationKeyUseCase: GetConfirmationKeyUseCase
//): BaseUseCase {
//
//    suspend operator fun invoke(steamId: Long, tag: String): Result<String> {
//        return getSteamAccountUseCase.invoke(steamId).mapCatchingInData { steamAccount ->
//            getConfirmationKeyUseCase.invoke(identitySecret = steamAccount.identitySecret, tag = tag)
//        }
//    }
//}
