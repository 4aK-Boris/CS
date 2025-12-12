package dmitriy.losev.cs.pulse

enum class Market(
    val title: String,
    val minSales: Int,
    val filterSalesId: Int,
) {
    STEAM(title = "Steam", minSales = 100, filterSalesId = 0),
    CS_MARKET(title = "Tm", minSales = 50, filterSalesId = 0),
    CS_FLOAT(title = "CsFloat", minSales = 50, filterSalesId = 140133),
    LIS_SKINS(title = "LisSkins", minSales = 50, filterSalesId = 0),
    BUFF(title = "Buff", minSales = 50, filterSalesId = 0);

    companion object {

        fun findMarketByTitle(title: String): Market {
            return entries.firstOrNull { market -> market.title == title } ?: error(
                message = "Неизвестный маркет с названием: $title. Доступные маркеты: ${entries.joinToString(transform = Market::title)}"
            )
        }
    }
}
