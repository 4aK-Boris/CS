package dmitriy.losev.cs.mappers

import java.time.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Factory
@OptIn(ExperimentalTime::class)
class DateTimeMapper {

    fun map(instant: Instant): LocalDateTime {
        return instant.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
    }
}