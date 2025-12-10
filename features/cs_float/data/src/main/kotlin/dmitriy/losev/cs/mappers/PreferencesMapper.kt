package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.PreferencesDSO
import dmitriy.losev.cs.dto.PreferencesDTO
import org.koin.core.annotation.Factory

@Factory
internal class PreferencesMapper {

    fun map(value: PreferencesDSO): PreferencesDTO {
        return PreferencesDTO(
            localizeItemNames = value.localizeItemNames,
            maxOfferDiscount = value.maxOfferDiscount,
            offersEnabled = value.offersEnabled
        )
    }
}
