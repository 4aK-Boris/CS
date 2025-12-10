package dmitriy.losev.cs

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HMacCrypto {

    private val mac = Mac.getInstance(MAC_ALGORITHM)

    fun generateMac(key: ByteArray, data: ByteArray): ByteArray {
        val secretKeySpec = SecretKeySpec(key, MAC_ALGORITHM)
        mac.init(secretKeySpec)
        return mac.doFinal(data)
    }

    companion object {
        private const val MAC_ALGORITHM = "HmacSHA1"
    }
}
