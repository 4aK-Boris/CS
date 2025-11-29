package dmitriy.losev.ozon.mappers

import dmitriy.losev.ozon.dso.InspectParamsDSO
import dmitriy.losev.ozon.dto.InspectParamsDTO
import org.koin.core.annotation.Factory

@Factory
class InspectParamsMapper {

    fun map(value: InspectParamsDSO?): InspectParamsDTO? {
        return value?.let {
            InspectParamsDTO(
                a = value.a,
                m = value.m,
                d = value.d,
                s = value.s
            )
        }
    }
}