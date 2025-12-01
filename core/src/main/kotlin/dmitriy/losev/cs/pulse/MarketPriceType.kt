package dmitriy.losev.cs.pulse

enum class MarketPriceType(val title: String) {
    SELL(title = "Sell"),
    BUY(title = "Buy"),
    GUARANTEED(title = "Guaranteed");
}
