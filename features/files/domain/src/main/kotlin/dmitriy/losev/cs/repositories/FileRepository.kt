package dmitriy.losev.cs.repositories

import java.io.File

interface FileRepository {

    suspend fun getAllFilesInDirectory(path: String): List<File>

    suspend fun deleteFile(file: File)
}