package dmitriy.losev.cs

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class AesCrypto(private val context: Context) {

    private val key by lazy { loadKey() }
    private val secureRandom = SecureRandom()

    fun encrypt(data: String): ByteArray {

        val iv = ByteArray(GCM_IV_LENGTH)

        secureRandom.nextBytes(iv)

        val cipher = Cipher.getInstance(CYPHER_ALGORITHM).apply {
            init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))
        }

        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        val result = ByteArray(iv.size + encrypted.size)

        System.arraycopy(iv, 0, result, 0, iv.size)
        System.arraycopy(encrypted, 0, result, iv.size, encrypted.size)

        return result
    }

    fun decrypt(encryptedData: ByteArray): String {

        val iv = encryptedData.copyOfRange(0, GCM_IV_LENGTH)
        val encrypted = encryptedData.copyOfRange(GCM_IV_LENGTH, encryptedData.size)

        val cipher = Cipher.getInstance(CYPHER_ALGORITHM).apply {
            init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))
        }

        val decrypted = cipher.doFinal(encrypted)

        return String(decrypted, Charsets.UTF_8)
    }

    private fun loadKey(): SecretKeySpec {
        val keyBytes = Base64.decode(context.aesKey.trim())
        return SecretKeySpec(keyBytes, KEY_ALGORITHM)
    }

    companion object {

        private const val KEY_ALGORITHM = "AES"
        private const val CYPHER_ALGORITHM = "AES/GCM/NoPadding"
        private const val GCM_IV_LENGTH = 12
        private const val GCM_TAG_LENGTH = 128
    }
}
