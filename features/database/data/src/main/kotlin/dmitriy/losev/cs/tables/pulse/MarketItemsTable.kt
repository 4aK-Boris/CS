package dmitriy.losev.cs.tables.pulse

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.javatime.CurrentTimestamp
import org.jetbrains.exposed.v1.javatime.timestamp

object MarketItemsTable : IntIdTable(name = "pulse.market_items") {
    val marketHashName = varchar(name = "market_hash_name", length = 255)

    val market = varchar(name = "market", length = 50)

    val minPrice = integer(name = "min_price")
        .nullable()
        .default(defaultValue = null)

    val minPriceUpdatedAt = timestamp(name = "min_price_updated_at")
        .nullable()
        .default(defaultValue = null)

    val buyOrderPrice = integer(name = "buy_order_price")
        .nullable()
        .default(defaultValue = null)

    val buyOrderUpdatedAt = timestamp(name = "buy_order_updated_at")
        .nullable()
        .default(defaultValue = null)

    val tradeOnPrice = integer(name = "trade_on_price")
        .nullable()
        .default(defaultValue = null)
    val tradeOnPriceUpdatedAt = timestamp(name = "trade_on_price_updated_at")
        .nullable()
        .default(defaultValue = null)

    val recommendedSellPrice = integer(name = "recommended_sell_price")
        .nullable()
        .default(defaultValue = null)

    val recommendedSellPriceUpdatedAt = timestamp(name = "recommended_sell_price_updated_at")
        .nullable()
        .default(defaultValue = null)

    val recommendedBuyPrice = integer(name = "recommended_buy_price")
        .nullable()
        .default(defaultValue = null)

    val recommendedBuyPriceUpdatedAt = timestamp(name = "recommended_buy_price_updated_at")
        .nullable()
        .default(defaultValue = null)

    val recommendedBuyOrdersCount = integer(name = "recommended_buy_orders_count")
        .nullable()
        .default(defaultValue = null)

    val weeklySalesCount = integer(name = "weekly_sales_count")

    val salesUpdatedAt = timestamp(name = "sales_updated_at")

    val createdAt = timestamp(name = "created_at").defaultExpression(CurrentTimestamp)

    init {

        uniqueIndex(marketHashName, market)

        index(false, marketHashName)
        index(false, market)
    }
}
