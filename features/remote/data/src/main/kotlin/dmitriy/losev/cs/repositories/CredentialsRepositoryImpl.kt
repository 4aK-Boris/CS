package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.FileHandler
import dmitriy.losev.cs.dto.CredentialsDTO
import dmitriy.losev.cs.mappers.CredentialsMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [CredentialsRepository::class])
internal class CredentialsRepositoryImpl(
    @Provided private val fileHandler: FileHandler,
    private val credentialsMapper: CredentialsMapper
): CredentialsRepository {

    override suspend fun getSteamCredentials(): List<CredentialsDTO> {
        return fileHandler.readFileLines(filePath = CREDENTIALS_FILE_PATH).map(transform = credentialsMapper::map)
    }

    companion object {
        private const val CREDENTIALS_FILE_PATH = "remote/src/main/resources/credentials.txt"
    }
}