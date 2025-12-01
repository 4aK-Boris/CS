package dmitriy.losev.cs.descriptions

import dmitriy.losev.cs.BaseDescription
import dmitriy.losev.cs.Context
import dmitriy.losev.cs.api.ApiResponse
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertSteamAccountResponseModel
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton
class SteamAccountDescription(@Provided private val context: Context): BaseDescription(isProd = context.environment.isProd) {

    fun upsertSteamAccountDescription(routeConfig: RouteConfig) {
        with(routeConfig) {
            tags(TAG)
            summary = "Добавить или обновить Steam аккаунт"
            description = "Создаёт новый аккаунт или обновляет существующий по steamId"
            operationId = "upsertSteamAccount"

            auth()

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

    companion object {
        private const val TAG = "Steam Account"
    }
}
