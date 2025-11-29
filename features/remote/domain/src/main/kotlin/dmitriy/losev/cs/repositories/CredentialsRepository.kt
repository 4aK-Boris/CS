package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.CredentialsDTO

interface CredentialsRepository {

    suspend fun getSteamCredentials(): List<CredentialsDTO>
}