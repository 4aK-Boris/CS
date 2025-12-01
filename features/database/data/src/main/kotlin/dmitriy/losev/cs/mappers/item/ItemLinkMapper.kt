package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.ItemLinkDSO
import dmitriy.losev.cs.dto.item.ItemLinkDTO
import org.koin.core.annotation.Factory

@Factory
internal class ItemLinkMapper {

    fun map(value: ItemLinkDSO): ItemLinkDTO {
        return ItemLinkDTO(
            classId = value.classId,
            instanceId = value.instanceId,
            listingId = value.listingId,
            link = value.link
        )
    }

    fun map(value: ItemLinkDTO): ItemLinkDSO {
        return ItemLinkDSO(
            classId = value.classId,
            instanceId = value.instanceId,
            listingId = value.listingId,
            link = value.link
        )
    }
}
