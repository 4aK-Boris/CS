package dmitriy.losev.cs

import java.io.File
import org.koin.core.annotation.Singleton

@Singleton
class FileHandler {

    fun readFile(filePath: String): String {
        return File(filePath).readText()
    }

    fun readFileLines(filePath: String): List<String> {
        return File(filePath).readLines()
    }

    fun createDirectories(mainDirectory: String, directories: List<String>) {
        File(mainDirectory).apply {
            directories.forEach { directory ->
                File(this, directory).mkdirs()
            }
        }
    }
}
