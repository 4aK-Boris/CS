package dmitriy.losev.cs.processor

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.Credentials
import dmitriy.losev.cs.di.RemoteModule
import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.dto.charm.CharmSaleOfferDTO
import dmitriy.losev.cs.main.ConsoleEncodingFix
import dmitriy.losev.cs.usecases.charm.CheckAndInsertCharmSaleOffersUseCase
import dmitriy.losev.cs.usecases.charm.InsertCharmSaleOffersUseCase
import dmitriy.losev.cs.usecases.charm.ProcessingFullCharmSaleOffersAndFloatUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import org.koin.ksp.generated.module

@OptIn(ExperimentalCoroutinesApi::class)
object Test : KoinComponent {

    private val context = Context(
        coroutineContext = Dispatchers.IO + SupervisorJob(),
        credentials = Credentials(
            databaseUser = "cs",
            databasePassword = "*L%HD!_4LRmhQ–X"
        ),
        proxyConfigs = listOf(
            ProxyConfig.Default(
                host = "mobpool.proxy.market",
                port = 10000,
                login = "p4p3OeHX1d9F",
                password = "85Kc04a6"
            )
        )
    )

    private val module = module {

        single { context }

        includes(RemoteModule().module)
    }

    private val processingFullCharmSaleOffersAndFloatUseCase by inject<ProcessingFullCharmSaleOffersAndFloatUseCase>()

    private fun Long.convertToULong(): ULong {
        return this.toULong() - Long.MIN_VALUE.toULong()
    }

    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {

        startKoin { modules(module) }

        ConsoleEncodingFix.fix()

        val charmInfo = CharmInfoDTO(name = "Charm | Die-cast AK", classId = 6216347003UL, instanceId = 0UL)

        processingFullCharmSaleOffersAndFloatUseCase.invoke(charmInfo = charmInfo)
    }
}