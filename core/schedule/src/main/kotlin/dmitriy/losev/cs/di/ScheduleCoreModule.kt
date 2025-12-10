package dmitriy.losev.cs.di

import dmitriy.losev.cs.AdvancedCoroutineScheduler
import dmitriy.losev.cs.tasks.TaskLogger
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Module
class ScheduleCoreModule {

    @Singleton
    fun getAdvancedCoroutineScheduler(@Provided logger: TaskLogger): AdvancedCoroutineScheduler {
        return AdvancedCoroutineScheduler(
            defaultDispatcher = Dispatchers.IO,
            logger = logger
        )
    }
}
