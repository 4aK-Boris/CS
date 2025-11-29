package dmitriy.losev.cs.repositories

import java.io.File
import org.koin.core.annotation.Factory

@Factory(binds = [FileRepository::class])
class FileRepositoryImpl: FileRepository {

    override suspend fun deleteFile(file: File) {
        file.delete()
    }

    override suspend fun getAllFilesInDirectory(path: String): List<File> {
        return File(path).listFiles()?.toList() ?: emptyList()
    }
}