package dmitriy.losev.cs.core

import dmitriy.losev.cs.FileHandler
import dmitriy.losev.cs.TimeHandler
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton
internal class SteamGuard(
    @Provided private val fileHandler: FileHandler,
    @Provided private val timeHandler: TimeHandler
) {

    private val mac = Mac.getInstance(MAC_ALGORITHM)

    private val base64Encoder = Base64.getEncoder()
    private val base64Decoder = Base64.getDecoder()

    fun generateSteamGuardCode(sharedSecret: String): String {

        val secret = base64Decoder.decode(sharedSecret)

        val timeStep = timeHandler.currentTimeInSeconds / 30L

        val buffer = ByteBuffer
            .allocate(8)
            .order(ByteOrder.BIG_ENDIAN)
            .putLong(timeStep)

        val timeBytes = buffer.array()

        mac.init(SecretKeySpec(secret, MAC_ALGORITHM))

        val hmac = mac.doFinal(timeBytes)

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

        val buffer = ByteArray(8 + tag.length)

        for (i in 7 downTo 0) {
            buffer[7 - i] = (timeHandler.currentTimeInSeconds shr (8 * i)).toByte()
        }

        tag.toByteArray().copyInto(buffer, 8)

        val secretKey = SecretKeySpec(base64Decoder.decode(identitySecret), MAC_ALGORITHM)

        mac.init(secretKey)

        return base64Encoder.encodeToString(mac.doFinal(buffer))
    }

    fun encryptPassword(password: String, modulus: String, exponent: String): String {

        val mod = modulus.toBigInteger(16)
        val exp = exponent.toBigInteger(16)

        val keySpec = RSAPublicKeySpec(mod, exp)
        val keyFactory = KeyFactory.getInstance(PSA_ALGORITHM)
        val publicKey = keyFactory.generatePublic(keySpec)

        val encrypted = Cipher.getInstance(RSA_CIPHER_ALGORITHM).run {
            init(Cipher.ENCRYPT_MODE, publicKey)
            doFinal(password.toByteArray())
        }

        return base64Encoder.encodeToString(encrypted)
    }

    companion object {

        private const val MAC_ALGORITHM = "HmacSHA1"
        private const val PSA_ALGORITHM = "RSA"
        private const val RSA_CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"
        private val STEAM_CHARS = "23456789BCDFGHJKMNPQRTVWXY".toCharArray()
    }
}