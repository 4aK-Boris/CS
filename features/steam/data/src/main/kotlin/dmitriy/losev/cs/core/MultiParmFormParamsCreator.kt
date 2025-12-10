package dmitriy.losev.cs.core

class MultiParmFormParamsCreator {

    fun createBoundaryAndFormText(openIdParams: String, nonce: String): Pair<String, ByteArray> {

        val boundary = "----WebKitFormBoundary${generateBoundary()}"

        val body = buildMultipartBody(
            boundary,
            listOf(
                "action" to "steam_openid_login",
                "openid.mode" to "checkid_setup",
                "openidparams" to openIdParams,
                "nonce" to nonce
            )
        )

        return boundary to body
    }

    private fun buildMultipartBody(boundary: String, fields: List<Pair<String, String>>): ByteArray {
        return buildString {
            fields.forEach { (name, value) ->
                append("--$boundary\r\n")
                append("Content-Disposition: form-data; name=\"$name\"\r\n")
                append("\r\n")
                append(value)
                append("\r\n")
            }
            append("--$boundary--")
        }.toByteArray(charset = Charsets.UTF_8)
    }

    private fun generateBoundary(): String {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..16).map { chars.random() }.joinToString("")
    }
}
