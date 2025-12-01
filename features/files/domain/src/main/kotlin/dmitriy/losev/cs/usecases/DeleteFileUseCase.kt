package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.repositories.FileRepository
import java.io.File
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided


@Factory
class DeleteFileUseCase(@Provided private val fileRepository: FileRepository) : BaseUseCase {

    suspend operator fun invoke(file: File): Result<Unit> = runCatching {
        fileRepository.deleteFile(file)
    }
}
