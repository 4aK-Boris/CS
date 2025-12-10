package dmitriy.losev.cs.core

import dmitriy.losev.cs.EMPTY_STRING
import dmitriy.losev.cs.HMacCrypto
import dmitriy.losev.cs.RsaCrypto
import dmitriy.losev.cs.TimeHandler
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.io.encoding.Base64

internal class SteamGuard(
    private val timeHandler: TimeHandler,
    private val rsaCrypto: RsaCrypto,
    private val hMacKeySpec: HMacCrypto
) {

    fun generateSteamGuardCode(sharedSecret: String): String {

        val secret = Base64.decode(sharedSecret)

        val timeStep = timeHandler.steamGuardCodeTime

        val buffer = ByteBuffer
            .allocate(8)
            .order(ByteOrder.BIG_ENDIAN)
            .putLong(timeStep)

        val timeBytes = buffer.array()

        val hmac = hMacKeySpec.generateMac(key = secret, data = timeBytes)

        val offset = (hmac.last().toInt() and 0x0F)

        var codePoint =
            ((hmac[offset].toInt() and 0x7F) shl 24) or
                    ((hmac[offset + 1].toInt() and 0xFF) shl 16) or
                    ((hmac[offset + 2].toInt() and 0xFF) shl 8) or
                    (hmac[offset + 3].toInt() and 0xFF)

        val out = CharArray(size = 5)

        repeat(times = 5) { index ->
            out[index] = STEAM_CHARS[codePoint % STEAM_CHARS.size]
            codePoint /= STEAM_CHARS.size
        }
        return String(chars = out)
    }

    fun generateConfirmationKey(identitySecret: String, tag: String): String {

        val secret = Base64.decode(identitySecret)

        val buffer = ByteArray(8 + tag.length)

        for (i in 7 downTo 0) {
            buffer[7 - i] = (timeHandler.currentTimeInSeconds shr (8 * i)).toByte()
        }

        tag.toByteArray().copyInto(buffer, 8)

        val hmac = hMacKeySpec.generateMac(key = secret, data = buffer)

        return Base64.encode(hmac)
    }

    fun encryptPassword(password: String, modulus: String, exponent: String): String {

        val mod = modulus.toBigInteger(16)
        val exp = exponent.toBigInteger(16)

        val encrypted = rsaCrypto.encrypt(data = password.toByteArray(), modulus = mod, exponent = exp)

        return Base64.encode(encrypted)
    }

    fun generateSessionId(length: Int = 24): String {
        return (1..length)
            .map { CHARS.random() }
            .joinToString(separator = EMPTY_STRING)
    }

    companion object {
        private val STEAM_CHARS = "23456789BCDFGHJKMNPQRTVWXY".toCharArray()
        private val CHARS = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray()
    }
}
