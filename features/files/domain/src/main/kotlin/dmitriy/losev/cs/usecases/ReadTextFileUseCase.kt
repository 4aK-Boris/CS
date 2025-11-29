package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.repositories.TextFileRepository
import java.io.File
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class ReadTextFileUseCase(@Provided private val textFileRepository: TextFileRepository): BaseUseCase {

    suspend operator fun invoke(file: File): Result<String> = runCatching {
        textFileRepository.readTextFile(file)
    }
}