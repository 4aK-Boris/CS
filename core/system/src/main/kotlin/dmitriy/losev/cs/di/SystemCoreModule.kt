package dmitriy.losev.cs.di

import dmitriy.losev.cs.FileHandler
import dmitriy.losev.cs.TimeHandler
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class SystemCoreModule {

    @Singleton
    fun getTimeHandler(): TimeHandler {
        return TimeHandler()
    }

    @Singleton
    fun getFileHandler(): FileHandler {
        return FileHandler()
    }
}
