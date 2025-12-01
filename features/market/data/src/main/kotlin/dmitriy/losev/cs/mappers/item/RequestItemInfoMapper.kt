package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.RequestItemInfoDSO
import org.koin.core.annotation.Factory

@Factory
class RequestItemInfoMapper {

    fun map(value: dmitriy.losev.cs.dto.item.RequestItemInfoDTO): RequestItemInfoDSO {
        return RequestItemInfoDSO(
            itemName = value.itemName,
            start = value.start.toString(),
            count = value.count.toString()
        )
    }
}


