package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.LinksDTO
import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import dmitriy.losev.cs.repositories.FloatCharmRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetCharmsFloatUseCase(@Provided private val charmRepository: FloatCharmRepository) : BaseUseCase {

    suspend operator fun invoke(classId: ULong, instanceId: ULong, links: LinksDTO): Result<List<CharmFloatDTO>> = runCatching {
        charmRepository.getCharmsFloat(classId, instanceId, links)
    }
}