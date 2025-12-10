package dmitriy.losev.cs.dto.auth.openid

data class GetRequestOpenIdParamsDTO(
    val steamId: Long,
    val claimedId: String = "http://specs.openid.net/auth/2.0/identifier_select",
    val identity: String = "http://specs.openid.net/auth/2.0/identifier_select",
    val mode: String = "checkid_setup",
    val ns: String = "http://specs.openid.net/auth/2.0",
    val realm: String,
    val returnTo: String
)
