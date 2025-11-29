package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import dmitriy.losev.cs.repositories.DatabaseCharmRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class InsertCharmFloatUseCase(@Provided private val databaseCharmRepository: DatabaseCharmRepository): BaseUseCase {

    suspend operator fun invoke(charmFloat: CharmFloatDTO): Result<CharmFloatDTO> = runCatching {
        databaseCharmRepository.insertCharmFloat(charmFloat)
    }
}