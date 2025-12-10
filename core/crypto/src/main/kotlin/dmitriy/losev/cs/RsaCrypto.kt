package dmitriy.losev.cs

import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import javax.crypto.Cipher

class RsaCrypto {

    private val cipher = Cipher.getInstance(CIPHER_ALGORITHM)

    fun encrypt(data: ByteArray, modulus: BigInteger, exponent: BigInteger): ByteArray {

        val keySpec = RSAPublicKeySpec(modulus, exponent)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val publicKey = keyFactory.generatePublic(keySpec)

        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        return cipher.doFinal(data)
    }

    companion object {
        private const val KEY_ALGORITHM = "RSA"
        private const val CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"
    }
}
