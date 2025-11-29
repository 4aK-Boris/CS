package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.core.Storage
import dmitriy.losev.cs.mappers.DateTimeMapper
import java.time.LocalDateTime
import org.koin.core.annotation.Factory

@Factory(binds = [DataStorePulseRepository::class])
class DataStorePulseRepositoryImpl(
    private val storage: Storage,
    private val dateTimeMapper: DateTimeMapper
) : DataStorePulseRepository {

    override suspend fun getAuthToken(): String {
        return storage.pulseAuthToken
    }

    override suspend fun setAuthToken(authToken: String): String {
        return authToken.apply {
            storage.pulseAuthToken = this
        }
    }

    override suspend fun getAuthTokenLastUpdate(): LocalDateTime {
        return dateTimeMapper.map(storage.pulseAuthTokenLastUpdate)
    }

    override suspend fun setAuthTokenLastUpdate() {
        storage.pulseAuthTokenLastUpdate = dateTimeMapper.map(value = currentDateTime)
    }

    private val currentDateTime: LocalDateTime
        get() = LocalDateTime.now()
}