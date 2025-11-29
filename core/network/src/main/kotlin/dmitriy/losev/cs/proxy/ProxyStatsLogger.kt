package dmitriy.losev.cs.proxy

object ProxyStatsLogger {

    fun logStats(stats: List<ProxyStats>, serviceName: String) {
        println("\n=== Статистика прокси для сервиса: $serviceName ===")
        println("┌─────────────────────────┬──────────┬─────────┬─────────┬─────────────┬───────────┬────────┐")
        println("│ Прокси                  │ Запросы  │ Успешно │ Ошибки  │ Среднее (мс)│ Успешность│ Статус │")
        println("├─────────────────────────┼──────────┼─────────┼─────────┼─────────────┼───────────┼────────┤")

        for (stat in stats) {
            val name = stat.name.padEnd(23)
            val requests = stat.requestCount.toString().padStart(8)
            val success = stat.successCount.toString().padStart(7)
            val failures = stat.failureCount.toString().padStart(7)
            val avgTime = stat.avgResponseTime.toString().padStart(11)
            val successRate = "${stat.successRate}%".padStart(9)
            val status = if (stat.isHealthy) "  OK  " else " FAIL "

            println("│ $name │ $requests │ $success │ $failures │ $avgTime │ $successRate │ $status │")
        }

        println("└─────────────────────────┴──────────┴─────────┴─────────┴─────────────┴───────────┴────────┘")

        // Итоговая статистика
        val totalRequests = stats.sumOf { it.requestCount }
        val totalSuccess = stats.sumOf { it.successCount }
        val totalFailures = stats.sumOf { it.failureCount }
        val overallSuccessRate = if (totalRequests > 0) {
            (totalSuccess.toFloat() / totalRequests * 100).toInt()
        } else {
            0
        }

        println("\nИтого:")
        println("  Всего запросов: $totalRequests")
        println("  Успешных: $totalSuccess")
        println("  Ошибок: $totalFailures")
        println("  Общая успешность: $overallSuccessRate%")
        println("  Здоровых прокси: ${stats.count { it.isHealthy }} / ${stats.size}")
        println("=".repeat(104))
        println()
    }

    fun logSimpleStats(stats: List<ProxyStats>, serviceName: String) {
        println("\n[$serviceName] Статистика прокси:")
        for (stat in stats) {
            println("  ${stat.name}: ${stat.requestCount} запросов, ${stat.successRate}% успешность, ${stat.avgResponseTime}мс")
        }
        println()
    }
}
