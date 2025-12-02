package dmitriy.losev.cs.descriptions

import dmitriy.losev.cs.BaseDescription
import dmitriy.losev.cs.api.ApiResponse
import dmitriy.losev.cs.models.DeleteProxyConfigRequestModel
import dmitriy.losev.cs.models.GetProxyConfigResponseModel
import dmitriy.losev.cs.models.GetSteamAccountProxyConfigResponseModel
import dmitriy.losev.cs.models.UpsertProxyConfigRequestModel
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Singleton

@Singleton
class ProxyDescription: BaseDescription() {

    fun upsertProxyConfigsDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(TAG)

            summary = "Добавить или обновить прокси-конфигурации"
            description = "Создаёт новые или обновляет существующие прокси-конфигурации"
            operationId = "upsertProxyConfigs"

            request {

                body<List<UpsertProxyConfigRequestModel>> {

                    required = true

                    description = "Список прокси-конфигураций"

                    example(name = "Список прокси-конфигураций") {
                        value = listOf(
                            UpsertProxyConfigRequestModel.example1,
                            UpsertProxyConfigRequestModel.example2
                        )
                    }

                    example(name = "Пустой список") {
                        value = emptyList<UpsertProxyConfigRequestModel>()
                    }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Прокси успешно добавлены"
                    body<ApiResponse.Success<Int>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun deleteProxyConfigDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(TAG)

            summary = "Удалить прокси-конфигурацию"
            description = "Удаляет прокси-конфигурацию"
            operationId = "deleteProxyConfig"

            request {

                body<DeleteProxyConfigRequestModel> {

                    required = true

                    description = "Хост и порт прокси-конфигурации"

                    example(name = "Хост и порт") {
                        value = DeleteProxyConfigRequestModel.example
                    }

                    example(name = "Пустая запись") {
                        value = DeleteProxyConfigRequestModel.emptyExample
                    }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Прокси успешно удалено"
                    body<ApiResponse.Success<Int>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun getProxyConfigsDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(TAG)

            summary = "Получить прокси-конфигурации"
            description = "Возвращает все прокси-конфигурации"
            operationId = "getProxyConfigs"

            response {
                HttpStatusCode.OK to {
                    description = "Возвращены все значения"
                    body<ApiResponse.Success<List<GetProxyConfigResponseModel>>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun getSteamAccountProxyConfigsDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(TAG)

            summary = "Получить прокси-конфигурации steam аккаунтов"
            description = "Возвращает все прокси-конфигурации steam аккаунтов"
            operationId = "getSteamAccountProxyConfigs"

            response {
                HttpStatusCode.OK to {
                    description = "Возвращены все значения"
                    body<ApiResponse.Success<List<GetSteamAccountProxyConfigResponseModel>>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    companion object {
        private const val TAG = "Proxy"
    }
}
