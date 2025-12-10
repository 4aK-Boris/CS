package dmitriy.losev.cs.dso.auth.openid

data class GetResponseOpenIdParamsDSO(
    val action: String,
    val mode: String,
    val openIdParams: String,
    val nonce: String
)
