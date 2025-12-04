package dmitriy.losev.cs.descriptions

import dmitriy.losev.cs.BaseDescription
import dmitriy.losev.cs.api.ApiResponse
import dmitriy.losev.cs.core.LOGIN_PARAMETER_EXAMPLE
import dmitriy.losev.cs.core.LOGIN_PARAMETER_NAME
import dmitriy.losev.cs.core.STEAM_ACCOUNT_TAG
import dmitriy.losev.cs.core.STEAM_ID_PARAMETER_EXAMPLE
import dmitriy.losev.cs.core.STEAM_ID_PARAMETER_NAME
import dmitriy.losev.cs.models.GetSteamAccountResponseModel
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertSteamAccountResponseModel
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Singleton

@Singleton
class SteamAccountDescription: BaseDescription() {

    fun upsertSteamAccountDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(STEAM_ACCOUNT_TAG)

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

    fun getSteamAccountBySteamIdDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(STEAM_ACCOUNT_TAG)

            summary = "Получить данные Steam аккаунта по SteamId"
            description = "Возвращает данные Steam аккаунта по SteamId"
            operationId = "getSteamAccountBySteamId"

            request {

                queryParameter<String>(name = STEAM_ID_PARAMETER_NAME) {

                    description = "SteamId аккаунта"
                    required = true

                    example(name = "Пример") { value = STEAM_ID_PARAMETER_EXAMPLE }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Данные Steam аккаунта"
                    body<ApiResponse.Success<GetSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun getSteamAccountByLoginDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(STEAM_ACCOUNT_TAG)

            summary = "Получить данные Steam аккаунта по логину"
            description = "Возвращает данные Steam аккаунта по логину"
            operationId = "getSteamAccountByLogin"

            request {

                queryParameter<String>(name = LOGIN_PARAMETER_NAME) {

                    description = "Логин аккаунта"
                    required = true

                    example(name = "Пример") { value = LOGIN_PARAMETER_EXAMPLE }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Данные Steam аккаунта"
                    body<ApiResponse.Success<GetSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun getAllSteamAccountDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(STEAM_ACCOUNT_TAG)

            summary = "Получить данные всех Steam аккаунтов"
            description = "Возвращает данные всех Steam аккаунтов"
            operationId = "getAllSteamAccounts"

            response {
                HttpStatusCode.OK to {
                    description = "Данные всех Steam аккаунтов"
                    body<ApiResponse.Success<List<GetSteamAccountResponseModel>>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun deleteSteamAccountBySteamIdDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(STEAM_ACCOUNT_TAG)

            summary = "Удалить аккаунт по SteamId"
            description = "Удаляет аккаунт по SteamId"
            operationId = "deleteSteamAccountBySteamId"

            request {

                queryParameter<String>(name = STEAM_ID_PARAMETER_NAME) {

                    description = "SteamId аккаунта"
                    required = true

                    example(name = "Пример") { value = STEAM_ID_PARAMETER_EXAMPLE }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Steam Id удалённого аккаунта"
                    body<ApiResponse.Success<GetSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }

    fun deleteSteamAccountByLoginDescription(routeConfig: RouteConfig) {

        with(routeConfig) {

            tags(STEAM_ACCOUNT_TAG)

            summary = "Удалить аккаунт по логину"
            description = "Удаляет аккаунт по логину"
            operationId = "deleteSteamAccountByLogin"

            request {

                queryParameter<String>(name = LOGIN_PARAMETER_NAME) {

                    description = "Логин аккаунта"
                    required = true

                    example(name = "Пример") { value = LOGIN_PARAMETER_EXAMPLE }
                }
            }

            response {
                HttpStatusCode.OK to {
                    description = "Steam Id удалённого аккаунта"
                    body<ApiResponse.Success<GetSteamAccountResponseModel>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Ошибка валидации"
                    body<ApiResponse.Error>()
                }
            }
        }
    }
}
