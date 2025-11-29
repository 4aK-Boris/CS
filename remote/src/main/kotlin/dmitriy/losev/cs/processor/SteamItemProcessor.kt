package dmitriy.losev.cs.processor

import dmitriy.losev.cs.dto.item.ItemInfoDTO
import dmitriy.losev.cs.usecases.GetSaleOffersForItemUseCase
import dmitriy.losev.cs.usecases.GetSingleItemCountUseCase
import dmitriy.losev.cs.usecases.item.GetItemsInfoUseCase
import dmitriy.losev.cs.usecases.item.InsertItemSaleOffersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.chunked
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

@ExperimentalCoroutinesApi
class SteamItemProcessor(
    private val scope: CoroutineScope,
    private val getItemsInfoUseCase: GetItemsInfoUseCase,
    private val getSingleItemCountUseCase: GetSingleItemCountUseCase,
    private val getSaleOffersForItemUseCase: GetSaleOffersForItemUseCase,
    private val insertItemSaleOffersUseCase: InsertItemSaleOffersUseCase,
    capacity: Int = 10,
) {

    private val itemInfo = MutableSharedFlow<ItemInfoDTO>(replay = 0, extraBufferCapacity = capacity, onBufferOverflow = BufferOverflow.DROP_LATEST)


//        .flatMapMerge(concurrency = 4) { itemInfo -> getItemSaleOffer(itemInfo) }
//        .chunked(size = 100)
//        .flatMapMerge(concurrency = 4) { itemSaleOffers -> getSaleOffers(ids = itemSaleOffers.map { it.second }) }
//        .chunked(size = 10)
//        .flatMapMerge(concurrency = 4) { saleOfferIds -> getItemFloat(links = saleOfferIds.map(transform = Long::toString)) }
//        .chunked(size = 10)
//        .map { float -> Random.nextLong() }

    private var addedCount = 0
    private var processedCount = 0


    private fun getItemCount(itemName: String): Flow<Pair<String, Int>> {
        return flow {
            delay(2000L)
            val count = when (itemName) {
                "AK" -> 10
                "AWP" -> 15
                "Deagle" -> 30
                else -> 50
            }
            emit(itemName to count)
        }
    }

    private fun getItemSaleOffer(itemInfo: Pair<String, Int>): Flow<Pair<String, Long>> {
        return flow {
            repeat(times = itemInfo.second) {
                emit(itemInfo.first to Random.nextLong())
            }
        }
    }



//    suspend fun start(delay: Long, count: Int) {
//
//        scope.launch {
//
//            while (true) {
//                getItemInfoFromDatabase(delay, count)
//                delay(600_000L)
//            }
//        }
//
//        scope.launch {
//            itemSaleOffers.collect { result ->
//                processedCount++
//                println("✅ Обработан: $result (всего обработано: $processedCount)")
//            }
//        }
//
//        while (true) {
//            delay(1000L)
//            val inQueue = addedCount - processedCount
//            println("📊 Статус: добавлено=$addedCount, обработано=$processedCount, в очереди=$inQueue, подписчиков=${itemNames.subscriptionCount.value}")
//        }
//    }
}