package dmitriy.losev.cs.dto

data class RSAKeyDTO(
    val success: Boolean,
    val publickeyModulus: String,
    val publickeyExponent: String,
    val timeStamp: Long
)
