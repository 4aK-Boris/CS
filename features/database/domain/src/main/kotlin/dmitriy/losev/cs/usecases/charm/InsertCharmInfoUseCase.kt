package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.exceptions.DatabaseException
import dmitriy.losev.cs.repositories.DatabaseCharmRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class InsertCharmInfoUseCase(@Provided private val databaseCharmRepository: DatabaseCharmRepository): BaseUseCase {

    suspend operator fun invoke(charmInfo: CharmInfoDTO): Result<String> = runCatching {
        databaseCharmRepository.insertCharmInfo(charmInfo) ?: throw DatabaseException.UnsuccessfulInsertCharmInfoException(name = charmInfo.name)
    }
}