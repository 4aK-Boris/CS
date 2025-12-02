package dmitriy.losev.cs.descriptions

import dmitriy.losev.cs.BaseDescription
import dmitriy.losev.cs.api.ApiResponse
import dmitriy.losev.cs.core.LOGIN_PARAMETER_EXAMPLE
import dmitriy.losev.cs.core.LOGIN_PARAMETER_NAME
import dmitriy.losev.cs.core.STEAM_ID_PARAMETER_EXAMPLE
import dmitriy.losev.cs.core.STEAM_ID_PARAMETER_NAME
import dmitriy.losev.cs.core.TAG
import dmitriy.losev.cs.models.GetActiveSteamAccountResponseModel
import dmitriy.losev.cs.models.GetSteamAccountResponseModel
import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertActiveSteamAccountResponseModel
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Singleton

@Singleton
class ActiveSteamAccountDescription: BaseDescription() {

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

    fun getActiveSteamAccountBySteamIdDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(TAG)

            summary = "Получить данные активного Steam аккаунта по SteamId"
            description = "Возвращает данные активного Steam аккаунта по SteamId"
            operationId = "getActiveSteamAccountBySteamId"

            request {

                queryParameter<String>(name = STEAM_ID_PARAMETER_NAME) {

                    description = "SteamId аккаунта"
                    required = true

                    example(name = "Пример") { value = STEAM_ID_PARAMETER_EXAMPLE }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Данные активного Steam аккаунта"
                    body<ApiResponse.Success<GetActiveSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun getActiveSteamAccountByLoginDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(TAG)

            summary = "Получить данные активного Steam аккаунта по логину"
            description = "Возвращает данные активного Steam аккаунта по логину"
            operationId = "getActiveSteamAccountByLogin"

            request {

                queryParameter<String>(name = LOGIN_PARAMETER_NAME) {

                    description = "Логин аккаунта"
                    required = true

                    example(name = "Пример") { value = LOGIN_PARAMETER_EXAMPLE }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Данные активного Steam аккаунта"
                    body<ApiResponse.Success<GetSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun getAllActiveSteamAccountDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(TAG)

            summary = "Получить данные всех активных Steam аккаунтов"
            description = "Возвращает данные всех активных Steam аккаунтов"
            operationId = "getAllSteamAccounts"

            response {
                HttpStatusCode.OK to {
                    description = "Данные всех активных steam аккаунтов"
                    body<ApiResponse.Success<List<GetSteamAccountResponseModel>>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }
}
