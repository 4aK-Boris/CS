package dmitriy.losev.cs.usecases.charm

//@Factory
//class CheckAndInsertCharmSaleOffersUseCase(
//    private val checkCharmSaleOffersUseCase: CheckCharmSaleOffersUseCase,
//    private val insertCharmSaleOffersUseCase: InsertCharmSaleOffersUseCase
//): BaseUseCase {
//
//    suspend operator fun invoke(charms: List<Triple<ULong, ULong, ListingInfoDTO>>): Result<List<CharmLinkDTO>> {
//        return checkCharmSaleOffersUseCase.invoke(charms).mapCatchingInData { charmSaleOffers ->
//            insertCharmSaleOffersUseCase.invoke(charmSaleOffers)
//        }
//    }
//}
