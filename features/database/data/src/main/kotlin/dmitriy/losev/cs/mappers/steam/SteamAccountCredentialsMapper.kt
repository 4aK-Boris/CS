package dmitriy.losev.cs.mappers.steam

import dmitriy.losev.cs.AesCrypto
import dmitriy.losev.cs.dso.steam.SteamAccountCredentialsDSO
import dmitriy.losev.cs.dto.steam.SteamAccountCredentialsDTO
import org.koin.core.annotation.Factory

@Factory
internal class SteamAccountCredentialsMapper(private val aesCrypto: AesCrypto) {

    fun map(value: SteamAccountCredentialsDSO): SteamAccountCredentialsDTO {
        return SteamAccountCredentialsDTO(
            steamId = value.steamId,
            login = value.login,
            password = aesCrypto.decrypt(encryptedData = value.password),
            sharedSecret = aesCrypto.decrypt(encryptedData = value.sharedSecret)
        )
    }
}
