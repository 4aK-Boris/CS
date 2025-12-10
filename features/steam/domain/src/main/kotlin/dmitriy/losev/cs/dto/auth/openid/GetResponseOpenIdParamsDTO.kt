package dmitriy.losev.cs.dto.auth.openid

data class GetResponseOpenIdParamsDTO(
    val action: String,
    val mode: String,
    val openIdParams: String,
    val nonce: String
)
