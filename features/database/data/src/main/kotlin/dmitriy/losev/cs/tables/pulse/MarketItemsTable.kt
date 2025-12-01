package dmitriy.losev.cs.tables.pulse

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.datetime

object MarketItemsTable: Table(name = "pulse.market_items") {

    val itemName = varchar("item_name", 255)

    val minBuyPrice = double("min_buy_price")

    val maxSellPrice = double("max_sell_price")

    val buyMarket = varchar("buy_market", 32)

    val sellMarket = varchar("sell_market", 32)

    val offersCount = integer("offers_count")

    val profit = double("profit")

    val lastUpdatedInBuyMarket = datetime(name = "last_updated_in_buy_market")

    val lastUpdatedInSellMarket = datetime(name = "last_updated_in_sell_market")

    val firstAddition = datetime("first_addition")

    override val primaryKey = PrimaryKey(firstColumn = itemName)
}
