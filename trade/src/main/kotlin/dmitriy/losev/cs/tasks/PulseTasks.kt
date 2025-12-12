package dmitriy.losev.cs.tasks

import dmitriy.losev.cs.AdvancedCoroutineScheduler
import dmitriy.losev.cs.TaskResult
import io.ktor.server.application.Application
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private const val UPDATE_MARKET_OFFERS = "update_market_offers"

fun Application.configurePulseTasks() {

    val scheduler by inject<AdvancedCoroutineScheduler>()
    val pulseTasks by inject<PulseTasks>()

    scheduler.scheduleWithPeriod(
        taskId = UPDATE_MARKET_OFFERS,
        period = 1.minutes,
        initialDelay = 10.seconds
    ) {
        TaskResult.fromResults(pulseTasks.getAndSaveOffersForAllMarketsPairTask())
    }
}
