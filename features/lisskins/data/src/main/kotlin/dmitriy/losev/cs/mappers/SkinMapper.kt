package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.SkinDSO
import dmitriy.losev.cs.dto.skins.SkinDTO
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Factory
class SkinMapper(
    private val dateTimeMapper: DateTimeMapper,
    private val stickerMapper: StickerMapper
) {

    fun map(value: SkinDSO): SkinDTO {
        return SkinDTO(
            id = value.id,
            name = value.name,
            price = value.price,
            unlockAt = value.unlockAt?.run(block = dateTimeMapper::map),
            itemClassId = value.itemClassId,
            createdAt = dateTimeMapper.map(value.createdAt),
            itemFloat = value.itemFloat.toDouble(),
            nameTag = value.nameTag,
            itemPaintIndex = value.itemPaintIndex,
            itemPaintSeed = value.itemPaintSeed,
            stickers = value.stickers.map(transform = stickerMapper::map)
        )
    }
}