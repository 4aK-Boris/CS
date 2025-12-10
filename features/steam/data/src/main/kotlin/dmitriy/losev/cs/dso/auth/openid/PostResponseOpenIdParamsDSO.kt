package dmitriy.losev.cs.dso.auth.openid

data class PostResponseOpenIdParamsDSO(
    val ns: String,
    val mode: String,
    val opEndpoint: String,
    val claimedId: String,
    val identity: String,
    val returnTo: String,
    val responseNonce: String,
    val assocHandle: String,
    val signed: String,
    val sig: String
)
