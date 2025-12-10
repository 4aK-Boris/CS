package dmitriy.losev.cs.tasks

import dmitriy.losev.cs.AdvancedCoroutineScheduler
import dmitriy.losev.cs.TaskConfig
import dmitriy.losev.cs.TaskResult
import io.ktor.server.application.Application
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

private const val UPDATE_ACCESS_TOKENS_FOR_STEAM_ACCOUNTS = "update_access_tokens_for_steam_accounts"
private const val UPDATE_REFRESH_TOKENS_FOR_STEAM_ACCOUNTS = "update_refresh_tokens_for_steam_accounts"
private const val UPDATE_CS_FLOAT_TOKENS_FOR_STEAM_ACCOUNTS = "update_cs_float_tokens_for_steam_accounts"

fun Application.configureSteamTasks() {

    val scheduler by inject<AdvancedCoroutineScheduler>()
    val updateTokensTask by inject<UpdateTokensForSteamAccountsTask>()

    // Обновление access токенов каждую минуту
    scheduler.scheduleWithPeriod(
        taskId = UPDATE_ACCESS_TOKENS_FOR_STEAM_ACCOUNTS,
        period = 1.minutes,
        config = TaskConfig(
            skipIfActive = setOf(
                UPDATE_REFRESH_TOKENS_FOR_STEAM_ACCOUNTS,
                UPDATE_CS_FLOAT_TOKENS_FOR_STEAM_ACCOUNTS
            )
        )
    ) {
        TaskResult.fromNestedResults(updateTokensTask.updateAccessTokensForSteamAccountsTask())
    }

    // Обновление refresh токенов каждый час
    scheduler.scheduleWithPeriod(
        taskId = UPDATE_REFRESH_TOKENS_FOR_STEAM_ACCOUNTS,
        period = 1.hours,
        config = TaskConfig(
            skipIfActive = setOf(
                UPDATE_ACCESS_TOKENS_FOR_STEAM_ACCOUNTS,
                UPDATE_CS_FLOAT_TOKENS_FOR_STEAM_ACCOUNTS
            )
        )
    ) {
        TaskResult.fromNestedResults(updateTokensTask.updateRefreshTokensForSteamAccountsTask())
    }

    // Обновление CS Float токенов каждые 5 минут
    scheduler.scheduleWithPeriod(
        taskId = UPDATE_CS_FLOAT_TOKENS_FOR_STEAM_ACCOUNTS,
        period = 5.minutes,
        config = TaskConfig(
            skipIfActive = setOf(
                UPDATE_ACCESS_TOKENS_FOR_STEAM_ACCOUNTS,
                UPDATE_REFRESH_TOKENS_FOR_STEAM_ACCOUNTS
            )
        )
    ) {
        TaskResult.fromNestedResults(updateTokensTask.updateCsFloatTokensForSteamAccountsTask())
    }
}
