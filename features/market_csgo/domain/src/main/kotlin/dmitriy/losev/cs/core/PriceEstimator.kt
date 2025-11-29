package dmitriy.losev.cs.core

import dmitriy.losev.cs.dto.sales.PriceEstimateDTO
import dmitriy.losev.cs.dto.sales.SaleDTO
import dmitriy.losev.cs.dto.sales.TrendDTO
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow

class PriceEstimator(
    private val outlierMultiplier: Double = 1.5,
    private val maxAgeDays: Long = 7,
    private val maxSamples: Int = 500,
    private val trendThresholdPercent: Double = 2.0  // меньше — считаем stable
) {

    fun estimate(
        sales: List<SaleDTO>,
        salesPerWeek: Int,
        now: Instant = Instant.now()
    ): PriceEstimateDTO? {

        if (sales.isEmpty()) return null

        val cutoff = now.minus(maxAgeDays, ChronoUnit.DAYS)

        val recent = sales
            .filter { sale -> sale.timestamp >= cutoff }
            .sortedByDescending(selector = SaleDTO::timestamp)
            .take(maxSamples)

        if (recent.isEmpty()) return null

        val (decayHours, percentile) = when {
            salesPerWeek >= 200 -> 8.0 to 0.40
            salesPerWeek >= 100 -> 12.0 to 0.35
            else -> 18.0 to 0.30
        }

        // Фильтрация выбросов
        val prices = recent.map(transform = SaleDTO::price).sorted()

        val q1 = prices.percentile(0.25)
        val q3 = prices.percentile(0.75)
        val iqr = q3 - q1
        val bounds = (q1 - outlierMultiplier * iqr)..(q3 + outlierMultiplier * iqr)

        val filtered = recent.filter { sale -> sale.price in bounds }.ifEmpty { recent }

        // Взвешивание
        val lambda = ln(2.0) / decayHours
        val weighted = filtered.map { sale ->
            val hoursAgo = Duration.between(sale.timestamp, now).toHours().coerceAtLeast(0)
            sale.price to exp(-lambda * hoursAgo)
        }

        val price = weightedPercentile(weighted, percentile)

        val confidence = when {
            filtered.size < 10 -> Confidence.LOW
            salesPerWeek < 80 -> Confidence.MEDIUM
            else -> Confidence.HIGH
        }

        // Тренд считаем по последним 48-72 часам
        val trend = calculateTrend(filtered, now)

        return PriceEstimateDTO(price, confidence, trend)
    }

    private fun calculateTrend(sales: List<SaleDTO>, now: Instant): TrendDTO? {

        if (sales.size < 10) return null

        // Берём продажи за последние 72 часа для анализа тренда
        val trendCutoff = now.minus(72, ChronoUnit.HOURS)
        val trendSales = sales.filter { it.timestamp >= trendCutoff }

        if (trendSales.size < 10) return null

        // Линейная регрессия: x = часы назад, y = цена
        val points = trendSales.map { sale ->
            val hoursAgo = Duration.between(sale.timestamp, now).toHours().toDouble()
            hoursAgo to sale.price
        }

        val n = points.size
        val sumX = points.sumOf(selector = Pair<Double, Double>::first)
        val sumY = points.sumOf(selector = Pair<Double, Double>::second)
        val sumXY = points.sumOf { (time, price) -> time * price }
        val sumX2 = points.sumOf { (time, _) -> time * time }

        val meanX = sumX / n
        val meanY = sumY / n

        val denominator = sumX2 - n * meanX * meanX
        if (denominator == 0.0) return null

        // slope отрицательный = цена растёт (т.к. x это "часы назад")
        val slope = (sumXY - n * meanX * meanY) / denominator

        // R² — коэффициент детерминации
        val ssRes = points.sumOf { (x, y) ->
            val predicted = meanY + slope * (x - meanX)
            (y - predicted).pow(2)
        }

        val ssTot = points.sumOf { (_, y) -> (y - meanY).pow(2) }
        val rSquared = if (ssTot > 0) 1 - (ssRes / ssTot) else 0.0

        // Переводим slope в % изменения за 24 часа
        // slope — это изменение цены за 1 час назад
        // отрицательный slope = цена была ниже раньше = рост
        val dailyChangePercent = (-slope * 24 / meanY) * 100

        val direction = when {
            dailyChangePercent > trendThresholdPercent -> TrendDirection.UP
            dailyChangePercent < -trendThresholdPercent -> TrendDirection.DOWN
            else -> TrendDirection.STABLE
        }

        return TrendDTO(
            direction = direction,
            dailyChangePercent = dailyChangePercent,
            strength = rSquared.coerceIn(0.0, 1.0)
        )
    }

    private fun weightedPercentile(data: List<Pair<Double, Double>>, percentile: Double): Double {

        if (data.isEmpty()) return 0.0

        val sorted = data.sortedBy(selector = Pair<Double, Double>::first)
        val totalWeight = sorted.sumOf(selector = Pair<Double, Double>::second)

        val targetWeight = totalWeight * percentile

        var cumulative = 0.0

        for ((price, weight) in sorted) {

            cumulative += weight

            if (cumulative >= targetWeight) {
                return price
            }
        }

        return sorted.last().first
    }

    private fun List<Double>.percentile(p: Double): Double {

        require(isNotEmpty()) { "List must not be empty" }
        require(p in 0.0..1.0) { "Percentile must be between 0 and 1" }

        val sorted = sorted()
        val index = p * (sorted.size - 1)
        val lower = index.toInt()
        val upper = (lower + 1).coerceAtMost(sorted.lastIndex)
        val fraction = index - lower

        return sorted[lower] + fraction * (sorted[upper] - sorted[lower])
    }

    private fun List<Double>.median(): Double = percentile(0.5)
}