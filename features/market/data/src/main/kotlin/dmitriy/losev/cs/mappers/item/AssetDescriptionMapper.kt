package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.AssetDescriptionDSO
import org.koin.core.annotation.Factory

@Factory
class AssetDescriptionMapper {

    fun map(value: AssetDescriptionDSO): dmitriy.losev.cs.dto.item.AssetDescriptionDTO {
        return _root_ide_package_.dmitriy.losev.cs.dto.item.AssetDescriptionDTO(
            classId = value.classId.toULong(),
            instanceId = value.instanceId.toULong(),
            tradable = value.tradable,
            type = _root_ide_package_.dmitriy.losev.cs.core.ItemType.getItemTypeByName(type = value.type),
            marketHashName = value.marketHashName,
        )
    }
}