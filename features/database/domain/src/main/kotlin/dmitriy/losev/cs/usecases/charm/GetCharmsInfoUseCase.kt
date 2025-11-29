package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.repositories.DatabaseCharmRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetCharmsInfoUseCase(@Provided private val databaseCharmRepository: DatabaseCharmRepository): BaseUseCase {

    suspend operator fun invoke(delay: Long, count: Int): Result<List<CharmInfoDTO>> = runCatching {
        databaseCharmRepository.getCharmsInfo(delay, count)
    }
}