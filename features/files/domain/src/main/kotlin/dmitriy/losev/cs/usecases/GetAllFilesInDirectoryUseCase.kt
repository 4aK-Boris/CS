package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.repositories.FileRepository
import java.io.File
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAllFilesInDirectoryUseCase(@Provided private val fileRepository: FileRepository) : BaseUseCase {

    suspend operator fun invoke(path: String): Result<List<File>> = runCatching {
        fileRepository.getAllFilesInDirectory(path)
    }
}