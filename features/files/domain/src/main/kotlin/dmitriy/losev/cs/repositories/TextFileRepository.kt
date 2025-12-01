package dmitriy.losev.cs.repositories

import java.io.File

interface TextFileRepository {

    suspend fun readTextFile(file: File): String
}
