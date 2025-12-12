package dmitriy.losev.cs.mappers

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import org.koin.core.annotation.Factory

@Factory
class DateTimeMapper {

    fun map(value: LocalDateTime): Long {
        return value.atZone(ZoneOffset.UTC).toInstant().epochSecond
    }

    fun map(value: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneOffset.UTC)
    }
}
