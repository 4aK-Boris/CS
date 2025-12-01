package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.ResponseItemInfoDSO
import org.koin.core.annotation.Factory

@Factory
class ResponseItemInfoMapper(
    private val searchDataMapper: SearchDataMapper,
    private val resultMapper: ResultMapper
) {

    fun map(value: ResponseItemInfoDSO): dmitriy.losev.cs.dto.item.ResponseItemInfoDTO {
        return _root_ide_package_.dmitriy.losev.cs.dto.item.ResponseItemInfoDTO(
            success = value.success,
            start = value.start,
            pagesize = value.pagesize,
            totalCount = value.totalCount,
            searchData = searchDataMapper.map(value.searchData),
            results = value.results.map(transform = resultMapper::map),
        )
    }
}