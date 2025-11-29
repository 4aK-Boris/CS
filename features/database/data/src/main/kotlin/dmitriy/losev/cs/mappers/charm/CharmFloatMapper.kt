package dmitriy.losev.cs.mappers.charm

import dmitriy.losev.cs.dso.charm.CharmFloatDSO
import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import dmitriy.losev.cs.mappers.LongMapper
import org.koin.core.annotation.Factory

@Factory
class CharmFloatMapper(private val longMapper: LongMapper) {

    fun map(value: CharmFloatDSO): CharmFloatDTO {
        return CharmFloatDTO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            itemId = longMapper.map(value = value.itemId),
            listingId = longMapper.map(value = value.listingId),
            pattern = value.pattern,
            a = longMapper.map(value = value.a),
            d = longMapper.map(value = value.d),
            m = longMapper.map(value = value.m)
        )
    }

    fun map(value: CharmFloatDTO): CharmFloatDSO {
        return CharmFloatDSO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            itemId = longMapper.map(value = value.itemId),
            listingId = longMapper.map(value = value.listingId),
            pattern = value.pattern,
            a = longMapper.map(value = value.a),
            d = longMapper.map(value = value.d),
            m = longMapper.map(value = value.m)
        )
    }
}