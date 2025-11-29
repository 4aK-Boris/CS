package dmitriy.losev.cs.pulse

enum class Market(
    val title: String,
    val minSales: Int,
    val filterSalesId: Int,
    val firstPriceType: MarketPriceType,
    val secondPriceType: MarketPriceType,
) {
    STEAM(title = "Steam", minSales = 100, filterSalesId = 0, firstPriceType = MarketPriceType.SELL, secondPriceType = MarketPriceType.GUARANTEED),
    CS_MARKET(title = "Tm", minSales = 50, filterSalesId = 0, firstPriceType = MarketPriceType.SELL, secondPriceType = MarketPriceType.GUARANTEED),
    CS_FLOAT(title = "CsFloat", minSales = 50, filterSalesId = 140133, firstPriceType = MarketPriceType.SELL, secondPriceType = MarketPriceType.SELL),
    LIS_SKINS(title = "LisSkins", minSales = 50, filterSalesId = 0, firstPriceType = MarketPriceType.SELL, secondPriceType = MarketPriceType.GUARANTEED),
    BUFF(title = "Buff", minSales = 50, filterSalesId = 0, firstPriceType = MarketPriceType.SELL, secondPriceType = MarketPriceType.GUARANTEED);

    companion object {

        fun findMarketByTitle(title: String): Market {
            return entries.firstOrNull { it.title == title } ?: throw IllegalArgumentException("Неизвестный маркет с названием: $title. Доступные маркеты: ${entries.joinToString { it.title }}")
        }
    }
}