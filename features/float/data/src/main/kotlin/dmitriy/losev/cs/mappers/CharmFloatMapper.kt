package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.ItemFloatDSO
import dmitriy.losev.cs.dso.ItemInfoFloatDSO
import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import org.koin.core.annotation.Factory

@Factory
class CharmFloatMapper {

    fun map(classId: ULong, instanceId: ULong, value: ItemFloatDSO): CharmFloatDTO {
        return CharmFloatDTO(
            classId = classId,
            instanceId = instanceId,
            itemId = value.itemId,
            listingId = value.m,
            pattern = value.keyChains.first().pattern,
            a = value.a,
            d = value.d,
            m = value.m
        )
    }

    fun map(classId: ULong, instanceId: ULong, value: ItemInfoFloatDSO): CharmFloatDTO {
        return CharmFloatDTO(
            classId = classId,
            instanceId = instanceId,
            itemId = value.itemInfoFloat.itemId,
            listingId = value.itemInfoFloat.m,
            pattern = value.itemInfoFloat.keyChains.first().pattern,
            a = value.itemInfoFloat.a,
            d = value.itemInfoFloat.d,
            m = value.itemInfoFloat.m
        )
    }
}