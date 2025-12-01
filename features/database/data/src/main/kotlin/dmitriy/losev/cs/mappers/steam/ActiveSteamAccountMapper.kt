package dmitriy.losev.cs.mappers.steam

import dmitriy.losev.cs.AesCrypto
import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import org.koin.core.annotation.Factory

@Factory
internal class ActiveSteamAccountMapper(private val aesCrypto: AesCrypto) {

    fun map(value: ActiveSteamAccountDTO): ActiveSteamAccountDSO {
        return ActiveSteamAccountDSO(
            steamId = value.steamId,
            marketCSGOApiToken = aesCrypto.encrypt(data = value.marketCSGOApiToken),
            accessToken = value.accessToken,
            refreshToken = aesCrypto.encrypt(data = value.refreshToken),
            sessionId = value.sessionId,
            createdAt = value.createdAt
        )
    }

    fun map(value: ActiveSteamAccountDSO): ActiveSteamAccountDTO {
        return ActiveSteamAccountDTO(
            steamId = value.steamId,
            marketCSGOApiToken = aesCrypto.decrypt(encryptedData = value.marketCSGOApiToken),
            accessToken = value.accessToken,
            refreshToken = aesCrypto.decrypt(encryptedData = value.refreshToken),
            sessionId = value.sessionId,
            createdAt = value.createdAt
        )
    }
}
