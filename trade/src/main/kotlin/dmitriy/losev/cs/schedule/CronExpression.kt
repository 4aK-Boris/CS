package dmitriy.losev.cs.schedule

import java.time.DayOfWeek
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Простая реализация cron-выражений
 */
data class CronExpression(
    val minute: Int? = null,        // 0-59
    val hour: Int? = null,          // 0-23
    val dayOfMonth: Int? = null,    // 1-31
    val month: Int? = null,         // 1-12
    val dayOfWeek: DayOfWeek? = null,
    val timeZone: ZoneId = ZoneId.systemDefault()
) {
    fun nextExecution(from: ZonedDateTime): ZonedDateTime {
        var next = from.plusMinutes(1).withSecond(0).withNano(0)

        while (true) {
            if ((minute == null || next.minute == minute) &&
                (hour == null || next.hour == hour) &&
                (dayOfMonth == null || next.dayOfMonth == dayOfMonth) &&
                (month == null || next.monthValue == month) &&
                (dayOfWeek == null || next.dayOfWeek == dayOfWeek)) {
                return next
            }
            next = next.plusMinutes(1)
        }
    }
}