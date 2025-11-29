package dmitriy.losev.cs.repositories

import java.io.File
import org.koin.core.annotation.Factory

@Factory(binds = [TextFileRepository::class])
class TextFileRepositoryImpl: TextFileRepository {

    override suspend fun readTextFile(file: File): String {
        return file.readText(charset = Charsets.UTF_8)
    }
}