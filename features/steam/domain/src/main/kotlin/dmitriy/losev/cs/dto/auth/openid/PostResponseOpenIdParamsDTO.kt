package dmitriy.losev.cs.dto.auth.openid

data class PostResponseOpenIdParamsDTO(
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
