package dmitriy.losev.cs

import org.koin.core.annotation.Singleton

@Singleton
class TimeHandler {

    val currentTimeInSeconds: Long
        get() = System.currentTimeMillis() / 1000

    val currentTime: Long
        get() = System.currentTimeMillis()
}
