package dmitriy.losev.cs.mappers

import org.koin.core.annotation.Factory

@Factory
internal class LongMapper {

    /**
     * Преобразует ULong в Long путем сдвига диапазона.
     * ULong диапазон [0, 18_446_744_073_709_551_615] -> Long диапазон [Long.MIN_VALUE, Long.MAX_VALUE]
     *
     * Формула: добавляем Long.MIN_VALUE в беззнаковой арифметике,
     * что эквивалентно инверсии старшего бита.
     *
     * Примеры:
     * - 0UL -> Long.MIN_VALUE
     * - ULong.MAX_VALUE / 2 -> -1L
     * - (ULong.MAX_VALUE / 2) + 1 -> 0L
     * - ULong.MAX_VALUE -> Long.MAX_VALUE
     */
    fun map(value: ULong): Long {
        return (value + Long.MIN_VALUE.toULong()).toLong()
    }

    /**
     * Преобразует Long обратно в ULong путем обратного сдвига диапазона.
     * Long диапазон [Long.MIN_VALUE, Long.MAX_VALUE] -> ULong диапазон [0, 18_446_744_073_709_551_615]
     *
     * Формула: вычитаем Long.MIN_VALUE в беззнаковой арифметике,
     * что эквивалентно инверсии старшего бита.
     *
     * Примеры:
     * - Long.MIN_VALUE -> 0UL
     * - -1L -> ULong.MAX_VALUE / 2
     * - 0L -> (ULong.MAX_VALUE / 2) + 1
     * - Long.MAX_VALUE -> ULong.MAX_VALUE
     */
    fun map(value: Long): ULong {
        return value.toULong() - Long.MIN_VALUE.toULong()
    }
}
