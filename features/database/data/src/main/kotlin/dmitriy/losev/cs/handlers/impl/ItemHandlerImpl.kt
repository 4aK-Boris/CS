package dmitriy.losev.cs.handlers.impl

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.item.ItemFloatDSO
import dmitriy.losev.cs.dso.item.ItemInfoDSO
import dmitriy.losev.cs.dso.item.ItemLinkDSO
import dmitriy.losev.cs.dso.item.ItemSaleOfferDSO
import dmitriy.losev.cs.handlers.ItemHandler
import dmitriy.losev.cs.tables.ItemSaleOffersTable
import dmitriy.losev.cs.tables.ItemsFloatTable
import dmitriy.losev.cs.tables.ItemsInfoTable
import dmitriy.losev.cs.tables.RareItemPatternsTable
import java.time.LocalDateTime
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.greaterEq
import org.jetbrains.exposed.v1.core.less
import org.jetbrains.exposed.v1.core.lessEq
import org.jetbrains.exposed.v1.r2dbc.batchInsert
import org.jetbrains.exposed.v1.r2dbc.insertReturning
import org.jetbrains.exposed.v1.r2dbc.select
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.koin.core.annotation.Factory

@Factory(binds = [ItemHandler::class])
internal class ItemHandlerImpl(private val database: Database) : ItemHandler {

    override suspend fun insertItemInfo(itemInfo: ItemInfoDSO): String? = database.suspendTransaction {
        ItemsInfoTable
            .insertReturning(
                returning = listOf(ItemsInfoTable.name),
                ignoreErrors = true
            ) { insertStatement ->
                insertStatement.set(column = ItemsInfoTable.classId, value = itemInfo.classId)
                insertStatement.set(column = ItemsInfoTable.instanceId, value = itemInfo.instanceId)
                insertStatement.set(column = ItemsInfoTable.name, value = itemInfo.name)
            }
            .firstOrNull()
            ?.getOrNull(expression = ItemsInfoTable.name)
    }

    override suspend fun getItemsInfo(delay: Long, count: Int): List<ItemInfoDSO> {
        return mutableListOf<ItemInfoDSO>().apply {
            database.suspendTransaction {
                val threshold = LocalDateTime.now().minusSeconds(delay)
                ItemsInfoTable
                    .select(column = ItemsInfoTable.name)
                    .where { ItemsInfoTable.lastCheckTime less threshold }
                    .limit(count = count)
                    .map { resultRow ->
                        ItemInfoDSO(
                            name = resultRow.get(expression = ItemsInfoTable.name),
                            classId = resultRow.get(expression = ItemsInfoTable.classId),
                            instanceId = resultRow.get(expression = ItemsInfoTable.instanceId)
                        )
                    }
                    .toList(destination = this@apply)
            }
        }
    }

    override suspend fun insertItemSaleOffers(itemSaleOffers: List<ItemSaleOfferDSO>): List<ItemLinkDSO> = database.suspendTransaction {
        ItemSaleOffersTable
            .batchInsert(
                data = itemSaleOffers,
                ignore = true,
                shouldReturnGeneratedValues = true
            ) { itemSaleOffer ->
                set(column = ItemSaleOffersTable.classId, value = itemSaleOffer.classId)
                set(column = ItemSaleOffersTable.instanceId, value = itemSaleOffer.instanceId)
                set(column = ItemSaleOffersTable.listingId, value = itemSaleOffer.listingId)
                set(column = ItemSaleOffersTable.priceForSeller, value = itemSaleOffer.priceForSeller)
                set(column = ItemSaleOffersTable.priceForBuyer, value = itemSaleOffer.priceForBuyer)
                set(column = ItemSaleOffersTable.assetId, value = itemSaleOffer.assetId)
                set(column = ItemSaleOffersTable.link, value = itemSaleOffer.link)
            }.map { resultRow ->
                ItemLinkDSO(
                    classId = resultRow.get(expression = ItemSaleOffersTable.classId),
                    instanceId = resultRow.get(expression = ItemSaleOffersTable.instanceId),
                    listingId = resultRow.get(expression = ItemSaleOffersTable.listingId),
                    link = resultRow.get(expression = ItemSaleOffersTable.link)
                )
            }
    }

    override suspend fun insertItemsFloat(itemsFloat: List<ItemFloatDSO>): Unit = database.suspendTransaction {
        ItemsFloatTable
            .batchInsert(
                data = itemsFloat,
                ignore = true,
                shouldReturnGeneratedValues = false
            ) { itemFloat ->
                set(column = ItemsFloatTable.classId, value = itemFloat.classId)
                set(column = ItemsFloatTable.instanceId, value = itemFloat.instanceId)
                set(column = ItemsFloatTable.itemId, value = itemFloat.itemId)
                set(column = ItemsFloatTable.listingId, value = itemFloat.listingId)
                set(column = ItemsFloatTable.float, value = itemFloat.float)
                set(column = ItemsFloatTable.pattern, value = itemFloat.pattern)
                set(column = ItemsFloatTable.s, value = itemFloat.s)
                set(column = ItemsFloatTable.a, value = itemFloat.a)
                set(column = ItemsFloatTable.d, value = itemFloat.d)
                set(column = ItemsFloatTable.m, value = itemFloat.m)
            }
    }

    override suspend fun findListingIdForRarePattern(itemFloat: ItemFloatDSO): ULong? = database.suspendTransaction {
        RareItemPatternsTable
            .selectAll()
            .where {
                (RareItemPatternsTable.classId eq itemFloat.classId) and
                        (RareItemPatternsTable.instanceId eq itemFloat.instanceId) and
                        (RareItemPatternsTable.startPattern lessEq itemFloat.pattern) and
                        (RareItemPatternsTable.endPattern greaterEq itemFloat.pattern)
            }
            .orderBy(column = RareItemPatternsTable.minimalPrice, order = SortOrder.DESC)
            .limit(count = 1)
            .map { itemFloat.listingId }
            .firstOrNull()
    }
}