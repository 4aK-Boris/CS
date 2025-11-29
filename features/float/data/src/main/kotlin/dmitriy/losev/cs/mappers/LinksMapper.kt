package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.LinkDSO
import dmitriy.losev.cs.dso.LinksDSO
import dmitriy.losev.cs.dto.LinksDTO
import org.koin.core.annotation.Factory

@Factory
class LinksMapper {

    fun map(value: LinksDTO): LinksDSO {
        return LinksDSO(links = value.links.map { link -> LinkDSO(link) })
    }
}