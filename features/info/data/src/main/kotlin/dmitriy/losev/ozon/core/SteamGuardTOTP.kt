package dmitriy.losev.ozon.core

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Утилита для генерации Steam Guard TOTP кодов
 */
object SteamGuardTOTP {

    private const val MAC_ALGORITHM = "HmacSHA1"
    private val STEAM_CHARS = "23456789BCDFGHJKMNPQRTVWXY".toCharArray()

    /**
     * Генерирует Steam Guard код из shared secret
     *
     * @param sharedSecret shared secret в формате Base64 из .maFile
     * @return 5-символьный Steam Guard код
     */
    fun generateCode(sharedSecret: String): String {
        val secret = Base64.getDecoder().decode(sharedSecret)

        // Текущий временной интервал (30 секунд)
        val timeStep = System.currentTimeMillis() / 30000L

        // Конвертируем время в байты (Big Endian)
        val timeBytes = ByteBuffer
            .allocate(8)
            .order(ByteOrder.BIG_ENDIAN)
            .putLong(timeStep)
            .array()

        // Генерируем HMAC-SHA1
        val mac = Mac.getInstance(MAC_ALGORITHM)
        mac.init(SecretKeySpec(secret, MAC_ALGORITHM))
        val hmac = mac.doFinal(timeBytes)

        // Получаем offset из последнего байта
        val offset = (hmac.last().toInt() and 0x0F)

        // Извлекаем 4 байта начиная с offset
        var codePoint =
            ((hmac[offset].toInt() and 0x7F) shl 24) or
            ((hmac[offset + 1].toInt() and 0xFF) shl 16) or
            ((hmac[offset + 2].toInt() and 0xFF) shl 8) or
            (hmac[offset + 3].toInt() and 0xFF)

        // Конвертируем в Steam-специфичный формат (5 символов)
        val code = CharArray(5)
        repeat(5) { index ->
            code[index] = STEAM_CHARS[codePoint % STEAM_CHARS.size]
            codePoint /= STEAM_CHARS.size
        }

        return String(code)
    }
}