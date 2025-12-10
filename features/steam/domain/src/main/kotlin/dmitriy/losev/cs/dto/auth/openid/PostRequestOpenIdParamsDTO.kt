package dmitriy.losev.cs.dto.auth.openid

data class PostRequestOpenIdParamsDTO(
    val steamId: Long,
    val action: String,
    val mode: String,
    val openIdParams: String,
    val nonce: String
)
