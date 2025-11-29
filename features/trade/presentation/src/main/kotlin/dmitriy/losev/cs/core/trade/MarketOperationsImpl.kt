package dmitriy.losev.cs.core.trade

import dmitriy.losev.cs.core.graph.context.PurchasedItem
import dmitriy.losev.cs.core.graph.market.ItemToBuy
import dmitriy.losev.cs.core.graph.market.MarketOperations
import dmitriy.losev.cs.core.graph.result.SaleResult
import dmitriy.losev.cs.core.graph.strategy.ItemSelector
import dmitriy.losev.cs.core.graph.strategy.PriceConstraint
import dmitriy.losev.cs.core.graph.strategy.PriceStrategy
import dmitriy.losev.cs.core.market.Market
import org.koin.core.annotation.Singleton

@Singleton
class MarketOperationsImpl: MarketOperations {

    override suspend fun buyItem(
        steamId: ULong,
        market: Market,
        itemSelector: ItemSelector,
        maxPrice: PriceConstraint
    ): PurchasedItem {
        TODO("Not yet implemented")
    }

    override suspend fun sellItem(
        steamId: ULong,
        market: Market,
        item: PurchasedItem,
        priceStrategy: PriceStrategy
    ): SaleResult {
        TODO("Not yet implemented")
    }

    override suspend fun waitForTradeLock(item: PurchasedItem) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForItemInInventory(steamId: ULong, item: PurchasedItem) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForSaleCompleted(steamId: ULong, saleId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForFundsUnlocked(steamId: ULong, market: Market, saleId: String, minDaysToWait: Int?) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForBalanceAvailable(steamId: ULong, market: Market, minAmountCents: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForMarketCooldown(steamId: ULong, market: Market, operationType: String) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForCustomCondition(steamId: ULong, checkInterval: Long, maxWaitSeconds: Long, description: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getBalance(steamId: ULong, market: Market): Long {
        TODO("Not yet implemented")
    }

    override suspend fun areFundsUnlocked(steamId: ULong, market: Market, saleId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun selectItemsForBatchBuy(
        steamId: ULong,
        market: Market,
        itemSelector: ItemSelector,
        maxPrice: PriceConstraint
    ): List<ItemToBuy> {
        TODO("Not yet implemented")
    }

    override suspend fun buyItemById(
        steamId: ULong,
        market: Market,
        itemId: String,
        priceCents: Long
    ): PurchasedItem {
        TODO("Not yet implemented")
    }

    override suspend fun buyItemsInParallel(
        steamId: ULong,
        market: Market,
        items: List<ItemToBuy>
    ): List<PurchasedItem> {
        TODO("Not yet implemented")
    }

    override suspend fun sellItemsInParallel(
        steamId: ULong,
        market: Market,
        items: List<PurchasedItem>,
        priceStrategy: PriceStrategy
    ): List<SaleResult> {
        TODO("Not yet implemented")
    }
}