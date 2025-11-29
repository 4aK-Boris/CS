package dmitriy.losev.cs.handlers.impl

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.charm.CharmFloatDSO
import dmitriy.losev.cs.dso.charm.CharmInfoDSO
import dmitriy.losev.cs.dso.charm.CharmLinkDSO
import dmitriy.losev.cs.dso.charm.CharmSaleOfferDSO
import dmitriy.losev.cs.handlers.CharmHandler
import dmitriy.losev.cs.tables.CharmSaleOffersTable
import dmitriy.losev.cs.tables.CharmsFloatTable
import dmitriy.losev.cs.tables.CharmsInfoTable
import dmitriy.losev.cs.tables.ItemsInfoTable
import java.time.LocalDateTime
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.less
import org.jetbrains.exposed.v1.r2dbc.batchInsert
import org.jetbrains.exposed.v1.r2dbc.insertIgnore
import org.jetbrains.exposed.v1.r2dbc.insertReturning
import org.jetbrains.exposed.v1.r2dbc.select
import org.koin.core.annotation.Factory

@Factory(binds = [CharmHandler::class])
internal class CharmHandlerImpl(private val database: Database) : CharmHandler {

    override suspend fun insertCharmInfo(charmInfo: CharmInfoDSO): String? = database.suspendTransaction {
        CharmsInfoTable
            .insertReturning(
                returning = listOf(ItemsInfoTable.name),
                ignoreErrors = true
            ) { insertStatement ->
                insertStatement.set(column = CharmsInfoTable.classId, value = charmInfo.classId)
                insertStatement.set(column = CharmsInfoTable.instanceId, value = charmInfo.instanceId)
                insertStatement.set(column = CharmsInfoTable.name, value = charmInfo.name)
            }
            .firstOrNull()
            ?.getOrNull(expression = CharmsInfoTable.name)
    }

    override suspend fun getCharmsInfo(delay: Long, count: Int): List<CharmInfoDSO> {
        return mutableListOf<CharmInfoDSO>().apply {
            database.suspendTransaction {
                val threshold = LocalDateTime.now().minusSeconds(delay)
                CharmsInfoTable
                    .select(column = CharmsInfoTable.name)
                    .where { CharmsInfoTable.lastCheckTime less threshold }
                    .limit(count = count)
                    .map { resultRow ->
                        CharmInfoDSO(
                            name = resultRow.get(expression = CharmsInfoTable.name),
                            classId = resultRow.get(expression = CharmsInfoTable.classId),
                            instanceId = resultRow.get(expression = CharmsInfoTable.instanceId)
                        )
                    }
                    .toList(destination = this@apply)
            }
        }
    }

    override suspend fun checkCharmSaleOffers(charmSaleOffers: List<CharmSaleOfferDSO>): List<CharmSaleOfferDSO> = database.suspendTransaction {

        if (charmSaleOffers.isEmpty()) return@suspendTransaction emptyList()

        val listingIds = charmSaleOffers.map(transform = CharmSaleOfferDSO::listingId)

        val existingListingIds = CharmSaleOffersTable
            .select(column = CharmSaleOffersTable.listingId)
            .where { CharmSaleOffersTable.listingId inList listingIds }
            .map { resultRow -> resultRow.get(expression = CharmSaleOffersTable.listingId) }
            .toSet()

        charmSaleOffers.filter { charmSaleOffer -> charmSaleOffer.listingId !in existingListingIds }
    }

    override suspend fun insertCharmSaleOffers(charmSaleOffers: List<CharmSaleOfferDSO>): List<CharmLinkDSO> = database.suspendTransaction {
        CharmSaleOffersTable
            .batchInsert(
                data = charmSaleOffers,
                ignore = true,
                shouldReturnGeneratedValues = true
            ) { itemSaleOffer ->
                set(column = CharmSaleOffersTable.classId, value = itemSaleOffer.classId)
                set(column = CharmSaleOffersTable.instanceId, value = itemSaleOffer.instanceId)
                set(column = CharmSaleOffersTable.listingId, value = itemSaleOffer.listingId)
                set(column = CharmSaleOffersTable.priceForSeller, value = itemSaleOffer.priceForSeller)
                set(column = CharmSaleOffersTable.priceForBuyer, value = itemSaleOffer.priceForBuyer)
                set(column = CharmSaleOffersTable.assetId, value = itemSaleOffer.assetId)
                set(column = CharmSaleOffersTable.link, value = itemSaleOffer.link)
            }.map { resultRow ->
                CharmLinkDSO(
                    classId = resultRow.get(expression = CharmSaleOffersTable.classId),
                    instanceId = resultRow.get(expression = CharmSaleOffersTable.instanceId),
                    listingId = resultRow.get(expression = CharmSaleOffersTable.listingId),
                    link = resultRow.get(expression = CharmSaleOffersTable.link)
                )
            }
    }

    override suspend fun insertCharmFloat(charmFloat: CharmFloatDSO): Unit = database.suspendTransaction {
        CharmsFloatTable
            .insertIgnore{ updateBuilder ->
                updateBuilder.set(column = CharmsFloatTable.classId, value = charmFloat.classId)
                updateBuilder.set(column = CharmsFloatTable.instanceId, value = charmFloat.instanceId)
                updateBuilder.set(column = CharmsFloatTable.itemId, value = charmFloat.itemId)
                updateBuilder.set(column = CharmsFloatTable.listingId, value = charmFloat.listingId)
                updateBuilder.set(column = CharmsFloatTable.pattern, value = charmFloat.pattern)
                updateBuilder.set(column = CharmsFloatTable.a, value = charmFloat.a)
                updateBuilder.set(column = CharmsFloatTable.d, value = charmFloat.d)
                updateBuilder.set(column = CharmsFloatTable.m, value = charmFloat.m)
            }
    }

    override suspend fun insertCharmsFloat(charmsFloat: List<CharmFloatDSO>): Unit = database.suspendTransaction {
        CharmsFloatTable
            .batchInsert(
                data = charmsFloat,
                ignore = true,
                shouldReturnGeneratedValues = false
            ) { charmFloat ->
                set(column = CharmsFloatTable.classId, value = charmFloat.classId)
                set(column = CharmsFloatTable.instanceId, value = charmFloat.instanceId)
                set(column = CharmsFloatTable.itemId, value = charmFloat.itemId)
                set(column = CharmsFloatTable.listingId, value = charmFloat.listingId)
                set(column = CharmsFloatTable.pattern, value = charmFloat.pattern)
                set(column = CharmsFloatTable.a, value = charmFloat.a)
                set(column = CharmsFloatTable.d, value = charmFloat.d)
                set(column = CharmsFloatTable.m, value = charmFloat.m)
            }
    }
}