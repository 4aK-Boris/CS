package dmitriy.losev.cs.usecases.account

//@Factory
//class AddSteamAccountsUseCase(
//    private val getAllFilesInDirectoryUseCase: GetAllFilesInDirectoryUseCase,
//    private val readTextFileUseCase: ReadTextFileUseCase,
//    private val deleteFileUseCase: DeleteFileUseCase,
//    private val insertSteamAccountFromJsonUseCase: UpsertSteamAccountUseCase
//): BaseUseCase {
//
//    suspend operator fun invoke(path: String): Result<Unit> {
//        return getAllFilesInDirectoryUseCase.invoke(path).mapCatching { files ->
//            files.forEach { file ->
//                readTextFileUseCase.invoke(file).mapCatchingInData { steamAccountString ->
//                    insertSteamAccountFromJsonUseCase.invoke(steamAccountString).onSuccess {
//                        deleteFileUseCase.invoke(file).throwException()
//                    }
//                }
//            }
//        }
//    }
//}
