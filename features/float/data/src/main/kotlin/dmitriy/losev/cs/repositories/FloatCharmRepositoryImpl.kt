package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.LinksDTO
import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import dmitriy.losev.cs.mappers.CharmFloatMapper
import dmitriy.losev.cs.mappers.LinksMapper
import dmitriy.losev.cs.network.FloatNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [FloatCharmRepository::class])
class FloatCharmRepositoryImpl(
    private val floatNetwork: FloatNetwork,
    private val linksMapper: LinksMapper,
    private val charmFloatMapper: CharmFloatMapper
): FloatCharmRepository {

    override suspend fun getCharmFloat(classId: ULong, instanceId: ULong, link: String): CharmFloatDTO {
        return charmFloatMapper.map(classId = classId, instanceId = instanceId, value = floatNetwork.getItemFloat(link))
    }

    override suspend fun getCharmsFloat(classId: ULong, instanceId: ULong, links: LinksDTO): List<CharmFloatDTO> {
        return floatNetwork.getItemsFloat(links = linksMapper.map(value = links)).items.values.map { itemFloat ->
            charmFloatMapper.map(classId = classId, instanceId = instanceId, value = itemFloat)
        }
    }
}