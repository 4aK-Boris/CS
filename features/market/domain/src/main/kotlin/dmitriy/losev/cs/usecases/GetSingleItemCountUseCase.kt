package dmitriy.losev.cs.usecases

import org.koin.core.annotation.Factory

@Factory
class GetSingleItemCountUseCase(private val getSingleItemInfoUseCase: GetSingleItemInfoUseCase): BaseUseCase {

    suspend operator fun invoke(itemName: String): Result<Int> = runCatching {
        getSingleItemInfoUseCase.invoke(itemName).getOrNull()?.results?.first()?.sellListings ?: 0
    }
}