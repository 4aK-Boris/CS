package dmitriy.losev.cs.mappers.sales

import java.time.Instant
import org.koin.core.annotation.Factory

@Factory
internal class InstantMapper {

    fun map(value: Long): Instant {
        return Instant.ofEpochSecond(value)
    }
}