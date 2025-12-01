package dmitriy.losev.cs.di

import dmitriy.losev.cs.AdvancedCoroutineScheduler
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class ScheduleCoreModule {

    @Singleton
    fun getAdvancedCoroutineScheduler(): AdvancedCoroutineScheduler {
        return AdvancedCoroutineScheduler(defaultDispatcher = Dispatchers.IO)
    }
}
