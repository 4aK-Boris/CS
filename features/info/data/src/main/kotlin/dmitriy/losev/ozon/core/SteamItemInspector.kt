package dmitriy.losev.ozon.core

import Cstrike15Gcmessages.CMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockRequest
import Cstrike15Gcmessages.CMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockResponse
import dmitriy.losev.ozon.core.ECsgoGCMsg.k_EMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockRequest
import dmitriy.losev.ozon.dso.InspectParamsDSO
import dmitriy.losev.ozon.dso.ItemInfoDSO
import `in`.dragonbra.javasteam.base.ClientGCMsgProtobuf
import `in`.dragonbra.javasteam.base.ClientMsgProtobuf
import `in`.dragonbra.javasteam.base.IPacketGCMsg
import `in`.dragonbra.javasteam.enums.EMsg
import `in`.dragonbra.javasteam.enums.EResult
import `in`.dragonbra.javasteam.protobufs.steamclient.SteammessagesClientserver
import `in`.dragonbra.javasteam.steam.handlers.steamgamecoordinator.SteamGameCoordinator
import `in`.dragonbra.javasteam.steam.handlers.steamgamecoordinator.callback.MessageCallback
import `in`.dragonbra.javasteam.steam.handlers.steamuser.LogOnDetails
import `in`.dragonbra.javasteam.steam.handlers.steamuser.SteamUser
import `in`.dragonbra.javasteam.steam.handlers.steamuser.callback.LoggedOnCallback
import `in`.dragonbra.javasteam.steam.steamclient.SteamClient
import `in`.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
class SteamItemInspector {
    private val steamClient = SteamClient()
    private val manager = CallbackManager(steamClient)
    private val steamUser = steamClient.getHandler(SteamUser::class.java)
    private val steamGameCoordinator = steamClient.getHandler(SteamGameCoordinator::class.java)

    private val isRunning = AtomicBoolean(value = true)
    private val callbackScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val isLogin = MutableStateFlow(value = false)
    private val isGameCoordinatorConnected = MutableStateFlow(value = false)
    private val itemInfo = MutableStateFlow<ItemInfoDSO?>(value = null)

    init {

        manager.subscribe(LoggedOnCallback::class.java, ::onLogin)
        manager.subscribe(MessageCallback::class.java, ::onGCMessage)
    }

    fun connect() {
        println("🔌 Connecting to Steam...")
        steamClient.connect()

        // Запускаем обработку callback'ов в фоновом режиме
        callbackScope.launch {
            while (isRunning.load()) {
                manager.runWaitCallbacks(1000L)
            }
        }
    }

    suspend fun login(login: String, password: String, totp: String): Boolean {
        println("🔐 Attempting to login as: $login")
        println("   TOTP code: $totp")

        val credentials = LogOnDetails().apply {
            this.username = login
            this.password = password
            this.twoFactorCode = totp
        }

        steamUser?.logOn(credentials)

        val result = withTimeoutOrNull(timeMillis = 30_000L) { isLogin.drop(1).first() } ?: false

        if (result.not()) {
            println("⏱️ Login timeout - no response received")
            disconnect()
        }

        return result
    }

    private fun onLogin(callback: LoggedOnCallback) {
        if (callback.result != EResult.OK) {
            println("❌ Steam login failed: ${callback.result}")
            println("   Extended result: ${callback.extendedResult}")
            isRunning.exchange(newValue = false)
            isLogin.value = false
        } else {
            println("✅ Successfully logged in to Steam")
            isLogin.value = true
        }
    }

    suspend fun launchCS(): Boolean {
        println("🎮 Launching CS2 (AppID: $CS2_GAME_ID)...")

        val playGame = ClientMsgProtobuf<SteammessagesClientserver.CMsgClientGamesPlayed.Builder>(
            SteammessagesClientserver.CMsgClientGamesPlayed::class.java,
            EMsg.ClientGamesPlayed
        )

        playGame.body.addGamesPlayed(SteammessagesClientserver.CMsgClientGamesPlayed.GamePlayed.newBuilder().setGameId(CS2_GAME_ID))

        steamClient.send(playGame)

        println("⏳ Waiting for Game Coordinator to connect...")

        // Ждем, пока Game Coordinator отправит ClientWelcome
        // После ClientGamesPlayed GC должен автоматически отправить Welcome
        val result = withTimeoutOrNull(timeMillis = 30_000L) { isGameCoordinatorConnected.drop(1).first() } ?: false

        if (result) {
            println("✅ Connected to CS2 Game Coordinator")
        } else {
            println("⏱️ Timeout waiting for Game Coordinator connection")
        }

        return result
    }


    suspend fun inspectItem(inspectParams: InspectParamsDSO): ItemInfoDSO? {
        println("🔍 Inspecting item...")
        println("   S: ${inspectParams.s}, A: ${inspectParams.a}, D: ${inspectParams.d}, M: ${inspectParams.m}")

        // Оборачиваем в ClientGCMsgProtobuf для отправки
        val gcMessage = ClientGCMsgProtobuf<CMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockRequest.Builder>(
            CMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockRequest::class.java,
            k_EMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockRequest
        )

        // Создаем запрос с параметрами из inspect ссылки
        // ULong.toLong() конвертирует беззнаковое в знаковое (bitwis e), что правильно для protobuf uint64
        gcMessage.body.setParamS(inspectParams.s.toLong())
        gcMessage.body.setParamA(inspectParams.a.toLong())
        gcMessage.body.setParamD(inspectParams.d.toLong())
        gcMessage.body.setParamM(inspectParams.m.toLong())

        // Отправляем запрос к Game Coordinator
        steamGameCoordinator?.send(gcMessage, CS2_GAME_ID.toInt())

        // Ждем ответ через flow
        val result = withTimeoutOrNull(timeMillis = 10_000L) {
            itemInfo.drop(1).first()
        }

        if (result == null) {
            println("⏱️ Timeout waiting for item info response")
        }

        // Очищаем после получения
        itemInfo.value = null

        return result
    }

    /**
     * Обработка сообщений от Game Coordinator
     */
    private fun onGCMessage(callback: MessageCallback) {
        val messageType = callback.eMsg
        val packetMessage = callback.message

        // Логируем все входящие GC сообщения
        println("📬 Received GC message: $messageType")

        when (messageType) {
            ECsgoGCMsg.k_EMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockResponse -> {
                handleInspectResponse(packetMessage)
            }
            ECsgoGCMsg.k_EMsgGCClientWelcome -> {
                println("✅ Received ClientWelcome from Game Coordinator")
                isGameCoordinatorConnected.value = true
            }
            ECsgoGCMsg.k_EMsgGCClientConnectionStatus -> {
                println("📡 Received ConnectionStatus from Game Coordinator")
                // ConnectionStatus означает, что GC готов к работе
                isGameCoordinatorConnected.value = true
            }
            ECsgoGCMsg.k_EMsgGCServerWelcome -> {
                println("✅ Received ServerWelcome from Game Coordinator")
                isGameCoordinatorConnected.value = true
            }
            else -> {
                println("   ⚠️ Unknown GC message type: $messageType")
            }
        }
    }

    private fun handleInspectResponse(gameCoordinatorMessage: IPacketGCMsg) {
        try {
            val response = ClientGCMsgProtobuf<CMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockResponse.Builder>(
                CMsgGCCStrike15_v2_Client2GCEconPreviewDataBlockResponse::class.java,
                gameCoordinatorMessage
            )

            println("📦 Получен ответ от GC")
            println("   Has iteminfo: ${response.body.hasIteminfo()}")

            // Используем Java protobuf API
            if (response.body.hasIteminfo()) {
                val iteminfo = response.body.iteminfo

                println("🔍 Детали предмета:")
                println("   AccountID: ${iteminfo.accountid}")
                println("   ItemID: ${iteminfo.itemid}")
                println("   DefIndex: ${iteminfo.defindex}")
                println("   PaintIndex: ${iteminfo.paintindex}")
                println("   PaintSeed: ${iteminfo.paintseed}")
                println("   PaintWear: ${iteminfo.paintwear}")
                println("   Rarity: ${iteminfo.rarity}")
                println("   Quality: ${iteminfo.quality}")
                println("   KillEater Type/Value: ${iteminfo.killeaterscoretype}/${iteminfo.killeatervalue}")
                println("   Origin: ${iteminfo.origin}")
                println("   CustomName: ${iteminfo.customname}")
                println("   Stickers Count: ${iteminfo.stickersCount}")
                println("   Keychains Count: ${iteminfo.keychainsCount}")
                println("   Inventory: ${iteminfo.inventory}")
                println("   EntIndex: ${iteminfo.entindex}")
                println("   PetIndex: ${iteminfo.petindex}")
                println("   MusicIndex: ${iteminfo.musicindex}")
                println("   QuestID: ${iteminfo.questid}")
                println("   DropReason: ${iteminfo.dropreason}")
                println("   Style: ${iteminfo.style}")
                println("   UpgradeLevel: ${iteminfo.upgradeLevel}")
                println("   Variations Count: ${iteminfo.variationsCount}")

                // Конвертируем paintwear из uint32 в float
                val floatValue = if (iteminfo.paintwear != 0) {
                    iteminfo.paintwear.toFloat() / 0xFFFFFFFF.toFloat()
                } else {
                    0f
                }

                // Парсим стикеры
                val stickers = iteminfo.stickersList.map { sticker ->
                    dmitriy.losev.ozon.dso.StickerDSO(
                        slot = sticker.slot,
                        stickerId = sticker.stickerId,
                        wear = sticker.wear,
                        scale = sticker.scale,
                        rotation = sticker.rotation,
                        tintId = sticker.tintId,
                        offsetX = sticker.offsetX,
                        offsetY = sticker.offsetY,
                        offsetZ = sticker.offsetZ,
                        pattern = sticker.pattern,
                        highlightReel = sticker.highlightReel
                    )
                }

                // Парсим брелки
                val keychains = iteminfo.keychainsList.map { keychain ->
                    dmitriy.losev.ozon.dso.StickerDSO(
                        slot = keychain.slot,
                        stickerId = keychain.stickerId,
                        wear = keychain.wear,
                        scale = keychain.scale,
                        rotation = keychain.rotation,
                        tintId = keychain.tintId,
                        offsetX = keychain.offsetX,
                        offsetY = keychain.offsetY,
                        offsetZ = keychain.offsetZ,
                        pattern = keychain.pattern,
                        highlightReel = keychain.highlightReel
                    )
                }

                // Парсим вариации
                val variations = iteminfo.variationsList.map { variation ->
                    dmitriy.losev.ozon.dso.StickerDSO(
                        slot = variation.slot,
                        stickerId = variation.stickerId,
                        wear = variation.wear,
                        scale = variation.scale,
                        rotation = variation.rotation,
                        tintId = variation.tintId,
                        offsetX = variation.offsetX,
                        offsetY = variation.offsetY,
                        offsetZ = variation.offsetZ,
                        pattern = variation.pattern,
                        highlightReel = variation.highlightReel
                    )
                }

                if (stickers.isNotEmpty()) {
                    println("   📌 Stickers:")
                    stickers.forEach { sticker ->
                        println("      Slot ${sticker.slot}: ID=${sticker.stickerId}, Wear=${sticker.wear}, Pattern=${sticker.pattern}")
                    }
                }

                if (keychains.isNotEmpty()) {
                    println("   🔑 Keychains:")
                    keychains.forEach { keychain ->
                        println("      Slot ${keychain.slot}: ID=${keychain.stickerId}, Wear=${keychain.wear}, Pattern=${keychain.pattern}")
                    }
                }

                val info = ItemInfoDSO(
                    accountId = iteminfo.accountid.toLong(),
                    itemId = iteminfo.itemid.toULong(),
                    defIndex = iteminfo.defindex,
                    paintIndex = iteminfo.paintindex,
                    rarity = iteminfo.rarity,
                    quality = iteminfo.quality,
                    floatValue = floatValue,
                    paintSeed = iteminfo.paintseed,
                    killEaterScoreType = iteminfo.killeaterscoretype,
                    killEaterValue = iteminfo.killeatervalue,
                    customName = iteminfo.customname ?: "",
                    stickers = stickers,
                    keychains = keychains,
                    variations = variations,
                    inventory = iteminfo.inventory,
                    origin = iteminfo.origin,
                    questId = iteminfo.questid,
                    dropReason = iteminfo.dropreason,
                    musicIndex = iteminfo.musicindex,
                    entIndex = iteminfo.entindex,
                    petIndex = iteminfo.petindex,
                    style = iteminfo.style,
                    upgradeLevel = iteminfo.upgradeLevel
                )

                itemInfo.value = info

                println("✅ Информация о предмете сохранена (${if (iteminfo.defindex == 0) "брелок/особый предмет" else "обычный предмет"})")
            } else {
                println("⚠️ Ответ не содержит iteminfo")
                itemInfo.value = null
            }

        } catch (e: Exception) {
            println("❌ Ошибка при обработке ответа: ${e.message}")
            e.printStackTrace()
            itemInfo.value = null
        }
    }

    fun disconnect() {
        steamUser?.logOff()
        steamClient.disconnect()
        isRunning.exchange(newValue = false)
    }

    companion object {
        private const val CS2_GAME_ID = 730L
    }
}