package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.SearchDataDSO
import org.koin.core.annotation.Factory

@Factory
class SearchDataMapper {

    fun map(value: SearchDataDSO): dmitriy.losev.cs.dto.item.SearchDataDTO {
        return _root_ide_package_.dmitriy.losev.cs.dto.item.SearchDataDTO(
            query = value.query,
            totalCount = value.totalCount,
            pagesize = value.pageSize,
        )
    }
}