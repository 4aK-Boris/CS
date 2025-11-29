package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.Credentials
import dmitriy.losev.cs.dto.CredentialsDTO
import org.koin.core.annotation.Factory

@Factory
internal class CredentialsMapper {

    fun map(value: String): CredentialsDTO {
        val credentials = value.split(':')
        return CredentialsDTO(login = credentials[0], password = credentials[1])
    }
}