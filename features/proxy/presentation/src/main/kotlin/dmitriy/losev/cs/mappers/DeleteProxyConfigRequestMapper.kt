package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.models.DeleteProxyConfigRequestModel
import org.koin.core.annotation.Factory

@Factory
class DeleteProxyConfigRequestMapper {

    fun map(value: DeleteProxyConfigRequestModel): Pair<String, Int> {
        return value.host to value.port
    }
}
