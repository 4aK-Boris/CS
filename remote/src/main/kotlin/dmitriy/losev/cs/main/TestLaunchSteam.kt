package dmitriy.losev.cs.main

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.di.RemoteModule
import dmitriy.losev.cs.usecases.LoginInSteamAndOpenCSUseCase
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

object TestLaunchSteam: KoinComponent {

    private val loginInSteamAndOpenCSUseCase by inject<LoginInSteamAndOpenCSUseCase>()

    private val context = Context()

    private val module = module {

        single { context }

        includes(RemoteModule().module)
    }

    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {

        startKoin {
            modules(module)
        }

        ConsoleEncodingFix.fix()


        loginInSteamAndOpenCSUseCase.invoke().getOrThrow()
    }
}