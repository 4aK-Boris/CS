package dmitriy.losev.cs.mappers.sales

import dmitriy.losev.cs.dso.sales.ItemsIdDSO
import dmitriy.losev.cs.dto.sales.ItemsIdDTO
import org.koin.core.annotation.Factory

@Factory
class ItemsIdMapper {

    fun map(value: ItemsIdDSO): ItemsIdDTO {
        return ItemsIdDTO(items = value.items)
    }
}