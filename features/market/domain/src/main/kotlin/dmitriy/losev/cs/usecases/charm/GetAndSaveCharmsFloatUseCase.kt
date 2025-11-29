package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmLinkDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory

@Factory
class GetAndSaveCharmsFloatUseCase(private val getAndSaveCharmFloatUseCase: GetAndSaveCharmFloatUseCase): BaseUseCase {

    suspend operator fun invoke(charmsLink: List<CharmLinkDTO>): Result<Unit> = runCatching {
        charmsLink.mapConcurrency { charmLink -> getAndSaveCharmFloatUseCase.invoke(charmLink) }
    }
}