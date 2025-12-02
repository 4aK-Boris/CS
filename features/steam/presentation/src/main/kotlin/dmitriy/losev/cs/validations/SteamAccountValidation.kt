package dmitriy.losev.cs.validations

import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import dmitriy.losev.cs.validation.BaseValidation
import io.konform.validation.Validation
import io.konform.validation.constraints.maxLength
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.pattern
import org.koin.core.annotation.Singleton

@Singleton
class SteamAccountValidation: BaseValidation {

    val validateUpsertSteamAccount = Validation {
        UpsertSteamAccountRequestModel::steamId {
            positiveLong()
        }
        UpsertSteamAccountRequestModel::login {
            minLength(1) hint "login is required"
            maxLength(64) hint "login is too long"
        }
        UpsertSteamAccountRequestModel::password {
            minLength(1) hint "password is required"
        }
        UpsertSteamAccountRequestModel::sharedSecret {
            minLength(1) hint "sharedSecret is required"
            pattern("[A-Za-z0-9+/=]+") hint "sharedSecret must be valid base64"
        }
        UpsertSteamAccountRequestModel::identitySecret {
            minLength(1) hint "identitySecret is required"
            pattern("[A-Za-z0-9+/=]+") hint "identitySecret must be valid base64"
        }
        UpsertSteamAccountRequestModel::revocationCode {
            pattern("R[0-9A-Z]+") hint "revocationCode must start with R"
        }
        UpsertSteamAccountRequestModel::deviceId {
            minLength(1) hint "deviceId is required"
        }
    }

    val validateUpsertActiveSteamAccount = Validation {
        UpsertActiveSteamAccountRequestModel::steamId {
            positiveLong()
        }
        UpsertActiveSteamAccountRequestModel::marketCSGOApiToken {
            minLength(31) hint "marketCSGOApiToken must be 31 characters long"
            maxLength(31) hint "marketCSGOApiToken must be 31 characters long"
        }
    }
}
