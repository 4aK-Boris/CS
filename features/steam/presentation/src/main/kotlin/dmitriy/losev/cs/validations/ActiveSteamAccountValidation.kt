package dmitriy.losev.cs.validations

import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import dmitriy.losev.cs.validation.BaseValidation
import io.konform.validation.Validation
import io.konform.validation.constraints.maxLength
import io.konform.validation.constraints.minLength
import org.koin.core.annotation.Singleton

@Singleton
class ActiveSteamAccountValidation : BaseValidation {

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
