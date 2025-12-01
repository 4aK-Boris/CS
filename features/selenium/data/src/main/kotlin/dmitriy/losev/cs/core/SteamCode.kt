package dmitriy.losev.cs.core

import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.time.Instant
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class SteamCode {

    fun generateCode(filePath: String): String {

        val json = Json.parseToJsonElement(string = File(filePath).readText()).jsonObject

        val sharedSecretB64 = when {
            json.contains(key = "shared_secret") -> json["shared_secret"]!!.jsonPrimitive.content
            json.contains(key = "sharedSecret") -> json["sharedSecret"]!!.jsonPrimitive.content
            else -> error("shared_secret not found in maFile")
        }

        val nowTime = Instant.now().epochSecond

        return generateSteamGuardCode(sharedSecretB64, nowTime)
    }

    private fun generateSteamGuardCode(sharedSecretB64: String, unixTime: Long): String {

        val secret = Base64.getDecoder().decode(sharedSecretB64)

        val timeStep = unixTime / 30L

        val buffer = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(timeStep)
        val timeBytes = buffer.array()

        val mac = Mac.getInstance("HmacSHA1")
        mac.init(SecretKeySpec(secret, "HmacSHA1"))
        val hmac = mac.doFinal(timeBytes)

        val offset = (hmac.last().toInt() and 0x0F)
        var codePoint =
            ((hmac[offset].toInt() and 0x7F) shl 24) or
                    ((hmac[offset + 1].toInt() and 0xFF) shl 16) or
                    ((hmac[offset + 2].toInt() and 0xFF) shl 8) or
                    (hmac[offset + 3].toInt() and 0xFF)

        val out = CharArray(5)
        repeat(5) { i ->
            out[i] = STEAM_CHARS[codePoint % STEAM_CHARS.size]
            codePoint /= STEAM_CHARS.size
        }
        return String(chars = out)
    }

    companion object {
        private val STEAM_CHARS = "23456789BCDFGHJKMNPQRTVWXY".toCharArray()
    }
}