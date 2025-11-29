package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.ItemInfoDSO
import dmitriy.losev.cs.dto.item.ItemInfoDTO
import org.koin.core.annotation.Factory

@Factory
class ItemInfoMapper {

    fun map(value: ItemInfoDSO): ItemInfoDTO {
        return ItemInfoDTO(
            classId = value.classId,
            instanceId = value.instanceId,
            name = value.name
        )
    }

    fun map(value: ItemInfoDTO): ItemInfoDSO {
        return ItemInfoDSO(
            classId = value.classId,
            instanceId = value.instanceId,
            name = value.name
        )
    }
}