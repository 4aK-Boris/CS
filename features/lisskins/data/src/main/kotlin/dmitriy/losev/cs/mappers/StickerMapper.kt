package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.StickerDSO
import dmitriy.losev.cs.dto.skins.StickerDTO
import org.koin.core.annotation.Factory

@Factory
class StickerMapper {

    fun map(value: StickerDSO): StickerDTO {
        return StickerDTO(
            image = value.image,
            name = value.name,
            slot = value.slot,
            wear = value.wear
        )
    }
}