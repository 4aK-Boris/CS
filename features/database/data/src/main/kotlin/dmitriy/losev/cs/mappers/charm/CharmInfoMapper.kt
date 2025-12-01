package dmitriy.losev.cs.mappers.charm

import dmitriy.losev.cs.dso.charm.CharmInfoDSO
import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.mappers.LongMapper
import org.koin.core.annotation.Factory

@Factory
internal class CharmInfoMapper(private val longMapper: LongMapper) {

    fun map(value: CharmInfoDSO): CharmInfoDTO {
        return CharmInfoDTO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            name = value.name
        )
    }

    fun map(value: CharmInfoDTO): CharmInfoDSO {
        return CharmInfoDSO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            name = value.name
        )
    }
}
