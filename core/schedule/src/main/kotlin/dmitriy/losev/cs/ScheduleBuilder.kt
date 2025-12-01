package dmitriy.losev.cs

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId

// Вспомогательные функции для создания расписаний
object ScheduleBuilder {
    fun businessHours(
        startHour: Int = 10,
        endHour: Int = 20,
        timeZone: ZoneId = ZoneId.systemDefault()
    ) = Schedule(
        startTime = LocalTime.of(startHour, 0),
        endTime = LocalTime.of(endHour, 0),
        workDays = Schedule.WEEKDAYS,
        timeZone = timeZone
    )

    fun customDays(
        days: Set<DayOfWeek>,
        startTime: LocalTime = LocalTime.MIN,
        endTime: LocalTime = LocalTime.MAX
    ) = Schedule(
        startTime = startTime,
        endTime = endTime,
        workDays = days
    )

    fun allTime() = Schedule(
        startTime = LocalTime.MIN,
        endTime = LocalTime.MAX,
        workDays = Schedule.ALL_DAYS
    )
}
