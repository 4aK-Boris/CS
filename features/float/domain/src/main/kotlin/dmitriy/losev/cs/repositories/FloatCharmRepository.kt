package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.LinksDTO
import dmitriy.losev.cs.dto.charm.CharmFloatDTO

interface FloatCharmRepository {

    suspend fun getCharmFloat(classId: ULong, instanceId: ULong, link: String): CharmFloatDTO

    suspend fun getCharmsFloat(classId: ULong, instanceId: ULong, links: LinksDTO): List<CharmFloatDTO>
}