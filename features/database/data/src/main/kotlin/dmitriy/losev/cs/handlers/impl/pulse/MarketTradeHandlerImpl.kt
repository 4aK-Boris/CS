package dmitriy.losev.cs.handlers.impl.pulse

import dmitriy.losev.cs.handlers.pulse.MarketTradeHandler
import org.koin.core.annotation.Factory

@Factory(binds = [MarketTradeHandler::class])
internal class MarketTradeHandlerImpl : MarketTradeHandler {

//    override suspend fun insertMarkerItems(markerItems: List<MarketItemDSO>): Unit = database.suspendTransaction {
//        MarketItemsTable.batchUpsert(
//            data = markerItems,
//            shouldReturnGeneratedValues = false,
//            onUpdateExclude = listOf(MarketItemsTable.firstAddition)
//        ) { markerItem ->
//            set(column = MarketItemsTable.itemName, value = markerItem.itemName)
//            set(column = MarketItemsTable.minBuyPrice, value = markerItem.minBuyPrice)
//            set(column = MarketItemsTable.maxSellPrice, value = markerItem.maxSellPrice)
//            set(column = MarketItemsTable.buyMarket, value = markerItem.buyMarket)
//            set(column = MarketItemsTable.sellMarket, value = markerItem.sellMarket)
//            set(column = MarketItemsTable.offersCount, value = markerItem.offersCount)
//            set(column = MarketItemsTable.profit, value = markerItem.profit)
//            set(column = MarketItemsTable.lastUpdatedInBuyMarket, value = markerItem.lastUpdatedInBuyMarket)
//            set(column = MarketItemsTable.lastUpdatedInSellMarket, value = markerItem.lastUpdatedInSellMarket)
//            set(column = MarketItemsTable.firstAddition, value = markerItem.firstAddition)
//        }
//    }
//
//    override suspend fun removeMarkerItems(): Unit = database.suspendTransaction {
//        MarketItemsTable.deleteWhere {
//            (MarketItemsTable.lastUpdatedInBuyMarket less minDateTime) or (MarketItemsTable.lastUpdatedInSellMarket less minDateTime)
//        }
//    }
//
//    override suspend fun getMarkerItems(buyMarket: String, sellMarket: String): List<MarketItemDSO> = database.suspendTransaction {
//        MarketItemsTable
//            .selectAll()
//            .where { (MarketItemsTable.buyMarket eq buyMarket) and (MarketItemsTable.sellMarket eq sellMarket) }
//            .orderBy(
//                MarketItemsTable.firstAddition to SortOrder.DESC,
//                MarketItemsTable.profit to SortOrder.DESC
//            )
//            .map(transform = ::convertToMarketItem)
//            .toList()
//    }

//    private fun convertToMarketItem(resultRow: ResultRow): MarketItemDSO {
//        return MarketItemDSO(
//            itemName = resultRow.get(expression = MarketItemsTable.itemName),
//            minBuyPrice = resultRow.get(expression = MarketItemsTable.minBuyPrice),
//            maxSellPrice = resultRow.get(expression = MarketItemsTable.maxSellPrice),
//            buyMarket = resultRow.get(expression = MarketItemsTable.buyMarket),
//            sellMarket = resultRow.get(expression = MarketItemsTable.sellMarket),
//            offersCount = resultRow.get(expression = MarketItemsTable.offersCount),
//            profit = resultRow.get(expression = MarketItemsTable.profit),
//            lastUpdatedInBuyMarket = resultRow.get(expression = MarketItemsTable.lastUpdatedInBuyMarket),
//            lastUpdatedInSellMarket = resultRow.get(expression = MarketItemsTable.lastUpdatedInSellMarket),
//            firstAddition = resultRow.get(MarketItemsTable.firstAddition)
//        )
//    }
//
//    private val minDateTime: LocalDateTime
//        get() = LocalDateTime.now().minusDays(14)
}
