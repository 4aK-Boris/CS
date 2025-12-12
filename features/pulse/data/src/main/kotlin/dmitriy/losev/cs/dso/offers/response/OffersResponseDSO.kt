package dmitriy.losev.cs.dso.offers.response

import dmitriy.losev.cs.core.OffersResponseSerializer
import kotlinx.serialization.Serializable

@Serializable(with = OffersResponseSerializer::class)
class OffersResponseDSO(items: List<OfferItemDSO>) : ArrayList<OfferItemDSO>(items)
