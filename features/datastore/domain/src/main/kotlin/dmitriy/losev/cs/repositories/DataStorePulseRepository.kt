package dmitriy.losev.cs.repositories

import java.time.LocalDateTime

interface DataStorePulseRepository {

    suspend fun getAuthToken(): String

    suspend fun setAuthToken(authToken: String): String

    suspend fun getAuthTokenLastUpdate(): LocalDateTime

    suspend fun setAuthTokenLastUpdate()
}
