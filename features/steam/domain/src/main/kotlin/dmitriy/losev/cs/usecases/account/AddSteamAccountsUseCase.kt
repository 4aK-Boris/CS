package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.DeleteFileUseCase
import dmitriy.losev.cs.usecases.GetAllFilesInDirectoryUseCase
import dmitriy.losev.cs.usecases.ReadTextFileUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import dmitriy.losev.cs.usecases.steam.InsertSteamAccountFromJsonUseCase
import dmitriy.losev.cs.usecases.throwException
import org.koin.core.annotation.Factory

@Factory
class AddSteamAccountsUseCase(
    private val getAllFilesInDirectoryUseCase: GetAllFilesInDirectoryUseCase,
    private val readTextFileUseCase: ReadTextFileUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val insertSteamAccountFromJsonUseCase: InsertSteamAccountFromJsonUseCase
): BaseUseCase {

    suspend operator fun invoke(path: String): Result<Unit> {
        return getAllFilesInDirectoryUseCase.invoke(path).mapCatching { files ->
            files.forEach { file ->
                readTextFileUseCase.invoke(file).mapCatchingInData { steamAccountString ->
                    insertSteamAccountFromJsonUseCase.invoke(steamAccountString).onSuccess {
                        deleteFileUseCase.invoke(file).throwException()
                    }
                }
            }
        }
    }
}