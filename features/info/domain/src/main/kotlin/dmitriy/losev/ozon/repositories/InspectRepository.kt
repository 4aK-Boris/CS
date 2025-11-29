package dmitriy.losev.ozon.repositories

import dmitriy.losev.ozon.dto.InspectParamsDTO

interface InspectRepository {

    suspend fun decodeLink(link: String): InspectParamsDTO?
}