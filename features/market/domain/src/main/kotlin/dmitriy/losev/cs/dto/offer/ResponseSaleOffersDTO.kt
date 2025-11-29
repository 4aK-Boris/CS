package dmitriy.losev.cs.dto.offer

import dmitriy.losev.cs.dto.ListingInfoDTO

data class ResponseSaleOffersDTO(
    val success: Boolean,
    val start: Int,
    val pageSize: Int,
    val totalCount: Int,
    val listingInfo: List<ListingInfoDTO>
)