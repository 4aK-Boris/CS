package dmitriy.losev.cs.mappers.steam

import dmitriy.losev.cs.dso.steam.Session
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class SteamAccountMapper(@Provided private val json: Json) {

    fun map(value: SteamAccountDTO): SteamAccountDSO {
        return SteamAccountDSO(
            accountName = value.accountName,
            sharedSecret = value.sharedSecret,
            serialNumber = value.serialNumber,
            revocationCode = value.revocationCode,
            uri = value.uri,
            serverTime = value.serverTime,
            tokenGid = value.tokenGid,
            identitySecret = value.identitySecret,
            secret = value.secret,
            status = value.status,
            deviceId = value.deviceId,
            fullyEnrolled = value.fullyEnrolled,
            session = Session(
                steamId = value.steamId,
                accessToken = value.accessToken,
                refreshToken = value.refreshToken,
                sessionId = value.sessionId
            )
        )
    }

    fun map(value: SteamAccountDSO): SteamAccountDTO {
        return SteamAccountDTO(
            accountName = value.accountName,
            sharedSecret = value.sharedSecret,
            serialNumber = value.serialNumber,
            revocationCode = value.revocationCode,
            uri = value.uri,
            serverTime = value.serverTime,
            tokenGid = value.tokenGid,
            identitySecret = value.identitySecret,
            secret = value.secret,
            status = value.status,
            deviceId = value.deviceId,
            fullyEnrolled = value.fullyEnrolled,
            steamId = value.session.steamId,
            accessToken = value.session.accessToken,
            refreshToken = value.session.refreshToken,
            sessionId = value.session.sessionId
        )
    }

    fun map(value: String): SteamAccountDSO {
        return json.decodeFromString(string = value)
    }
}