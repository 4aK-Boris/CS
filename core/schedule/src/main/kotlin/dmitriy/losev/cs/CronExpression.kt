package dmitriy.losev.cs

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

        val minuteExpression = minute == null || next.minute == minute
        val hourExpression = hour == null || next.hour == hour
        val dayOfMonthExpression = dayOfMonth == null || next.dayOfMonth == dayOfMonth
        val dayOfWeekExpression = dayOfWeek == null || next.dayOfWeek == dayOfWeek
        val monthExpression = month == null || next.monthValue == month

        while (true) {
            if (minuteExpression && hourExpression && dayOfMonthExpression && monthExpression && dayOfWeekExpression) {
                return next
            }
            next = next.plusMinutes(1)
        }
    }
}
