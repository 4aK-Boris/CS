package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.ResultDSO
import org.koin.core.annotation.Factory

@Factory
class ResultMapper(private val assetDescriptionMapper: AssetDescriptionMapper) {

    fun map(value: ResultDSO): dmitriy.losev.cs.dto.item.ResultDTO {
        return _root_ide_package_.dmitriy.losev.cs.dto.item.ResultDTO(
            name = value.name,
            sellListings = value.sellListings,
            sellPrice = value.sellPrice,
            assetDescription = assetDescriptionMapper.map(value.assetDescription),
        )
    }
}