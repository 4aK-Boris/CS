package dmitriy.losev.cs.schedule

import org.koin.core.component.KoinComponent

object Application: KoinComponent {

//
//    private const val ADD_STEAM_ACCOUNTS_TASK_ID = "add_steam_accounts_task_id"
//
//    private val advancedCoroutineScheduler = AdvancedCoroutineScheduler()
//
//    private val context = Context(
//        coroutineContext = SupervisorJob() + Dispatchers.IO,
//        credentials = Credentials(
//            databaseUser = "cs",
//            databasePassword = "*L%HD!_4LRmhQ–X"
//        ),
//        pulseConfig = PulseConfig(
//            templateId = 116255,
//            email = "ssiroshtann@mail.ru",
//            password = "Q1648937qwe"
//        )
//    )
//
//    val defaultModule = module {
//        single { context }
//    }
//
//    private val getItemsFromLisSkinsToCsMarketTasks by inject<LisSkinsToCsMarketTradeTasks>()
//    private val steamAccountTasks by inject<SteamAccountTasks>()
//
//    @JvmStatic
//    fun main(args: Array<String>): Unit = runBlocking {
//
//        ConsoleEncodingFix.fix()
//
//        startKoin {
//            modules(defaultModule, AppModule().module)
//        }
//
//        advancedCoroutineScheduler.scheduleWithPeriod(
//            taskId = GET_LIS_SKINS_TO_CS_MARKET_ITEMS_TASK_ID,
//            periodMillis = 300_000L,
//            task = getItemsFromLisSkinsToCsMarketTasks::startGetAndSaveLisSkinsToCsMarketItemsTask
//        )
//
//        advancedCoroutineScheduler.scheduleWithPeriod(
//            taskId = REMOVE_OLD_LIS_SKINS_TO_CS_MARKET_ITEMS_TASK_ID,
//            periodMillis = 600_000L,
//            task = getItemsFromLisSkinsToCsMarketTasks::startRemoveOldLisSkinsToCsMarketItemsTask
//        )
//
//        advancedCoroutineScheduler.scheduleWithPeriod(
//            taskId = ADD_STEAM_ACCOUNTS_TASK_ID,
//            periodMillis = 300_000L,
//            task = steamAccountTasks::startAddSteamAccountsTask
//        )
//
//        while (true) {
//            delay(1000L)
//        }
//    }
}
