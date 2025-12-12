package dmitriy.losev.cs.handlers.impl.pulse

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.pulse.MarketItemDSO
import dmitriy.losev.cs.handlers.pulse.MarketTradeHandler
import dmitriy.losev.cs.tables.pulse.MarketItemsTable
import java.time.Instant
import org.jetbrains.exposed.v1.r2dbc.upsert
import org.koin.core.annotation.Factory

@Factory(binds = [MarketTradeHandler::class])
internal class MarketTradeHandlerImpl(private val database: Database) : MarketTradeHandler {

    override suspend fun upsertMarkerItems(markerItems: List<MarketItemDSO>): Unit = database.suspendTransaction {
        markerItems.forEach { markerItem ->
            MarketItemsTable.upsert(
                MarketItemsTable.marketHashName,
                MarketItemsTable.market,
                onUpdateExclude = listOf(MarketItemsTable.createdAt)
            ) {
                it[marketHashName] = markerItem.marketHashName
                it[market] = markerItem.market
                it[weeklySalesCount] = markerItem.weeklySalesCount
                it[salesUpdatedAt] = currentTimeStamp

                markerItem.minPrice?.let { price ->
                    it[minPrice] = price
                    it[minPriceUpdatedAt] = currentTimeStamp
                }

                markerItem.buyOrderPrice?.let { price ->
                    it[buyOrderPrice] = price
                    it[buyOrderUpdatedAt] = currentTimeStamp
                }

                markerItem.tradeOnPrice?.let { price ->
                    it[tradeOnPrice] = price
                    it[tradeOnPriceUpdatedAt] = currentTimeStamp
                }
            }
        }
    }

    private val currentTimeStamp: Instant
        get() = Instant.now()
}
