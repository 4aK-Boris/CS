package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import dmitriy.losev.cs.usecases.charm.InsertCharmFloatUseCase
import org.koin.core.annotation.Factory

@Factory
class GetCharmFloatAndSaveToDatabaseUseCase(
    private val getCharmFloatUseCase: GetCharmFloatUseCase,
    private val insertCharmFloatUseCase: InsertCharmFloatUseCase
) : BaseUseCase {

    suspend operator fun invoke(classId: ULong, instanceId: ULong, link: String): Result<CharmFloatDTO> {
        return getCharmFloatUseCase.invoke(classId, instanceId, link).mapCatchingInData { charmFloat ->
            insertCharmFloatUseCase.invoke(charmFloat)
        }
    }
}