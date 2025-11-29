package dmitriy.losev.ozon.core

import java.security.Security
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.bouncycastle.jce.provider.BouncyCastleProvider

private val inspectLinkParser = InspectLinkParser()
private val steamItemInspector = SteamItemInspector()

fun main(): Unit = runBlocking {

    ConsoleEncodingFix.fix()

    Security.addProvider(BouncyCastleProvider())

    val link = "steam://rungame/730/76561202255233023/+csgo_econ_action_preview%20M664969621036785758A40445661671D9386277775298459193"

    val inspectParams = inspectLinkParser.parse(link)

    println(inspectParams)

    // Ваш shared secret из .maFile
    val sharedSecret = "XwFgBjr8NEdQqGJYIpVmECjMiK0=" // Замените на ваш реальный shared secret

    steamItemInspector.connect()

    delay(10000)

    // Генерируем TOTP код
    val totpCode = SteamGuardTOTP.generateCode(sharedSecret)
    println("Generated TOTP code: $totpCode")

    val loginResult = steamItemInspector.login(
        login = "steelcrow542",
        password = "PC\\=os3mE&TV=\\R",
        totp = totpCode
    )

    println("login result: $loginResult")

    if (loginResult) {
        val launchResult = steamItemInspector.launchCS()

        if (launchResult) {
            println("\n" + "=".repeat(80))
            println("ИНСПЕКЦИЯ ПРЕДМЕТА")
            println("=".repeat(80))

            val result = steamItemInspector.inspectItem(inspectParams!!)

            if (result != null) {
                println("\n" + "=".repeat(80))
                println("📊 ПОЛНАЯ ИНФОРМАЦИЯ О ПРЕДМЕТЕ")
                println("=".repeat(80))
                println()
                println("🆔 Идентификация:")
                println("   Account ID: ${result.accountId}")
                println("   Item ID: ${result.itemId}")
                println("   Def Index: ${result.defIndex}")
                println()
                println("🎨 Внешний вид:")
                println("   Paint Index: ${result.paintIndex}")
                println("   Paint Seed: ${result.paintSeed}")
                println("   Float Value: ${"%.10f".format(result.floatValue)}")
                println("   Rarity: ${result.rarity}")
                println("   Quality: ${result.quality}")
                println()
                if (result.customName.isNotEmpty()) {
                    println("✏️ Кастомное имя: \"${result.customName}\"")
                    println()
                }
                if (result.killEaterScoreType != 0 || result.killEaterValue != 0) {
                    println("📈 StatTrak/Souvenir:")
                    println("   Score Type: ${result.killEaterScoreType}")
                    println("   Value: ${result.killEaterValue}")
                    println()
                }
                if (result.stickers.isNotEmpty()) {
                    println("📌 Стикеры (${result.stickers.size}):")
                    result.stickers.forEach { sticker ->
                        println("   [Slot ${sticker.slot}]")
                        println("      ID: ${sticker.stickerId}")
                        println("      Wear: ${sticker.wear}")
                        println("      Scale: ${sticker.scale}")
                        println("      Rotation: ${sticker.rotation}")
                        if (sticker.pattern != 0) println("      Pattern: ${sticker.pattern}")
                    }
                    println()
                }
                if (result.keychains.isNotEmpty()) {
                    println("🔑 Брелки (${result.keychains.size}):")
                    result.keychains.forEach { keychain ->
                        println("   [Slot ${keychain.slot}]")
                        println("      ID: ${keychain.stickerId}")
                        println("      Wear: ${keychain.wear}")
                        println("      Pattern: ${keychain.pattern}")
                    }
                    println()
                }
                println("📦 Дополнительная информация:")
                println("   Inventory: ${result.inventory}")
                println("   Origin: ${result.origin}")
                if (result.questId != 0) println("   Quest ID: ${result.questId}")
                if (result.dropReason != 0) println("   Drop Reason: ${result.dropReason}")
                if (result.musicIndex != 0) println("   Music Index: ${result.musicIndex}")
                if (result.petIndex != 0) println("   Pet Index: ${result.petIndex}")
                if (result.style != 0) println("   Style: ${result.style}")
                if (result.upgradeLevel != 0) println("   Upgrade Level: ${result.upgradeLevel}")
                println()
                println("=".repeat(80))
            } else {
                println("\n❌ Не удалось получить информацию о предмете")
            }
        } else {
            println("\n❌ Не удалось подключиться к Game Coordinator")
        }
    } else {
        println("\n❌ Не удалось войти в Steam")
    }

    steamItemInspector.disconnect()
    println("\n✅ Отключено от Steam")
}