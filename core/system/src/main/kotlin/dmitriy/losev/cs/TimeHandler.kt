package dmitriy.losev.cs

import java.time.Instant
import org.koin.core.annotation.Singleton

@Singleton
class TimeHandler {

    val currentTimeInSeconds: Long
        get() = System.currentTimeMillis() / 1000

    val currentTime: Long
        get() = System.currentTimeMillis()
}