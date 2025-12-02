package dmitriy.losev.cs.descriptions

import dmitriy.losev.cs.BaseDescription
import dmitriy.losev.cs.api.ApiResponse
import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertActiveSteamAccountResponseModel
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertSteamAccountResponseModel
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Singleton

@Singleton
class SteamAccountDescription: BaseDescription() {

    fun upsertSteamAccountDescription(routeConfig: RouteConfig) {
        with(routeConfig) {
            tags(TAG)
            summary = "Добавить или обновить Steam аккаунт"
            description = "Создаёт новый аккаунт или обновляет существующий по steamId"
            operationId = "upsertSteamAccount"

            request {

                body<UpsertSteamAccountRequestModel> {

                    required = true

                    description = "Данные Steam аккаунта"

                    example(name = "Новый аккаунт") {
                        value = UpsertSteamAccountRequestModel.example
                    }

                    example(name = "Пустой аккаунт") {
                        value = UpsertSteamAccountRequestModel.emptyExample
                    }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Аккаунт успешно добавлен/обновлён"
                    body<ApiResponse.Success<UpsertSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun upsertActiveSteamAccountDescription(routeConfig: RouteConfig) {
        with(routeConfig) {
            tags(TAG)
            summary = "Добавить или обновить активный Steam аккаунт"
            description = "Создаёт новый аккаунт или обновляет активный по steamId"
            operationId = "upsertActiveSteamAccount"

            request {

                body<UpsertActiveSteamAccountRequestModel> {

                    required = true

                    description = "Данные активного Steam аккаунта"

                    example(name = "Новый аккаунт") {
                        value = UpsertActiveSteamAccountRequestModel.example
                    }

                    example(name = "Пустой аккаунт") {
                        value = UpsertActiveSteamAccountRequestModel.emptyExample
                    }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Аккаунт успешно активирован/обновлён"
                    body<ApiResponse.Success<UpsertActiveSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }



    companion object {
        private const val TAG = "Steam Account"
    }
}
