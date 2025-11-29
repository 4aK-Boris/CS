package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import dmitriy.losev.cs.dto.charm.CharmLinkDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.GetCharmFloatUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import org.koin.core.annotation.Factory

@Factory
class GetAndSaveCharmFloatUseCase(
    private val getCharmFloatUseCase: GetCharmFloatUseCase,
    private val insertCharmFloatUseCase: InsertCharmFloatUseCase
): BaseUseCase {

    suspend operator fun invoke(charmLink: CharmLinkDTO): Result<CharmFloatDTO> {
        return getCharmFloatUseCase.invoke(classId = charmLink.classId, instanceId = charmLink.instanceId, link = charmLink.link).mapCatchingInData { charmFloat ->
            insertCharmFloatUseCase.invoke(charmFloat)
        }
    }
}