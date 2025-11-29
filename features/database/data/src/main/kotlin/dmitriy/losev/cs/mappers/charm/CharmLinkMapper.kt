package dmitriy.losev.cs.mappers.charm

import dmitriy.losev.cs.dso.charm.CharmLinkDSO
import dmitriy.losev.cs.dto.charm.CharmLinkDTO
import dmitriy.losev.cs.mappers.LongMapper
import org.koin.core.annotation.Factory

@Factory
class CharmLinkMapper(private val longMapper: LongMapper) {

    fun map(value: CharmLinkDSO): CharmLinkDTO {
        return CharmLinkDTO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            listingId = longMapper.map(value = value.listingId),
            link = value.link
        )
    }

    fun map(value: CharmLinkDTO): CharmLinkDSO {
        return CharmLinkDSO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            listingId = longMapper.map(value = value.listingId),
            link = value.link
        )
    }
}