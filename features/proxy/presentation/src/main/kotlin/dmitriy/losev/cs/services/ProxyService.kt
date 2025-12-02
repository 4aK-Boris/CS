package dmitriy.losev.cs.services

import dmitriy.losev.cs.mappers.DeleteProxyConfigRequestMapper
import dmitriy.losev.cs.mappers.GetProxyConfigResponseMapper
import dmitriy.losev.cs.mappers.GetSteamAccountProxyConfigResponseMapper
import dmitriy.losev.cs.mappers.UpsertProxyConfigRequestMapper
import dmitriy.losev.cs.models.DeleteProxyConfigRequestModel
import dmitriy.losev.cs.models.GetProxyConfigResponseModel
import dmitriy.losev.cs.models.GetSteamAccountProxyConfigResponseModel
import dmitriy.losev.cs.models.UpsertProxyConfigRequestModel
import dmitriy.losev.cs.usecases.DeleteProxyConfigWithSwapProxyUseCase
import dmitriy.losev.cs.usecases.proxy.GetProxyConfigsUseCase
import dmitriy.losev.cs.usecases.proxy.GetSteamAccountProxyConfigsUseCase
import dmitriy.losev.cs.usecases.proxy.UpsertProxyConfigsUseCase
import org.koin.core.annotation.Single

@Single
class ProxyService(
    private val upsertProxyConfigsUseCase: UpsertProxyConfigsUseCase,
    private val deleteProxyConfigWithSwapProxyUseCase: DeleteProxyConfigWithSwapProxyUseCase,
    private val getProxyConfigsUseCase: GetProxyConfigsUseCase,
    private val getSteamAccountProxyConfigsUseCase: GetSteamAccountProxyConfigsUseCase,
    private val upsertProxyConfigRequestMapper: UpsertProxyConfigRequestMapper,
    private val deleteProxyConfigRequestMapper: DeleteProxyConfigRequestMapper,
    private val getProxyConfigResponseMapper: GetProxyConfigResponseMapper,
    private val getSteamAccountProxyConfigResponseMapper: GetSteamAccountProxyConfigResponseMapper
) {

    suspend fun upsertProxyConfigs(upsertProxyConfigRequestModels: List<UpsertProxyConfigRequestModel>): Result<Int> {
        return upsertProxyConfigsUseCase.invoke(proxyConfigs = upsertProxyConfigRequestModels.map(transform = upsertProxyConfigRequestMapper::map))
    }

    suspend fun deleteProxyConfig(deleteProxyConfigRequestModel: DeleteProxyConfigRequestModel): Result<Int> {
        val (host, port) = deleteProxyConfigRequestModel.toDTO()
        return deleteProxyConfigWithSwapProxyUseCase.invoke(host, port)
    }

    suspend fun getProxyConfigs(): Result<List<GetProxyConfigResponseModel>> {
        return getProxyConfigsUseCase.invoke().map { list -> list.map(transform = getProxyConfigResponseMapper::map) }
    }

    suspend fun getSteamAccountProxyConfigs(): Result<List<GetSteamAccountProxyConfigResponseModel>> {
        return getSteamAccountProxyConfigsUseCase.invoke().map { list -> list.map(transform = getSteamAccountProxyConfigResponseMapper::map) }
    }

    private fun DeleteProxyConfigRequestModel.toDTO(): Pair<String, Int> = deleteProxyConfigRequestMapper.map(value = this)
}
