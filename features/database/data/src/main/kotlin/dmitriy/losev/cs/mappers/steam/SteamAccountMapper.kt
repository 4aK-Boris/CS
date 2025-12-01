package dmitriy.losev.cs.mappers.steam

import dmitriy.losev.cs.AesCrypto
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import org.koin.core.annotation.Factory

@Factory
internal class SteamAccountMapper(private val aesCrypto: AesCrypto) {

    fun map(value: SteamAccountDTO): SteamAccountDSO {
        return SteamAccountDSO(
            steamId = value.steamId,
            login = value.login,
            password = aesCrypto.encrypt(data = value.password),
            sharedSecret = aesCrypto.encrypt(data = value.sharedSecret),
            identitySecret = aesCrypto.encrypt(data = value.identitySecret),
            revocationCode = aesCrypto.encrypt(data = value.revocationCode),
            deviceId = value.deviceId,
            createdAt = value.createdAt
        )
    }

    fun map(value: SteamAccountDSO): SteamAccountDTO {
        return SteamAccountDTO(
            steamId = value.steamId,
            login = value.login,
            password = aesCrypto.decrypt(encryptedData = value.password),
            sharedSecret = aesCrypto.decrypt(encryptedData = value.sharedSecret),
            identitySecret = aesCrypto.decrypt(encryptedData = value.identitySecret),
            revocationCode = aesCrypto.decrypt(encryptedData = value.revocationCode),
            deviceId = value.deviceId,
            createdAt = value.createdAt
        )
    }
}
