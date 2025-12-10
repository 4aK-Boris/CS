package dmitriy.losev.cs.core

import dmitriy.losev.cs.dso.auth.openid.GetResponseOpenIdParamsDSO
import dmitriy.losev.cs.dso.auth.openid.PostResponseOpenIdParamsDSO
import java.net.URLDecoder

internal class OpenIdExtractor {

    fun extractOpenIdParamsFromHTML(html: String): GetResponseOpenIdParamsDSO {
        val action = extractInputValue(html, name = "action") ?: error("Could not extract 'action' from OpenID form")
        val mode = extractInputValue(html, name = "openid.mode") ?: error("Could not extract 'openid.mode' from OpenID form")
        val openIdParams = extractInputValue(html, name = "openidparams") ?: error("Could not extract 'openidparams' from OpenID form")
        val nonce = extractInputValue(html, name = "nonce") ?: error("Could not extract 'nonce' from OpenID form")

        return GetResponseOpenIdParamsDSO(
            action = action,
            mode = mode,
            openIdParams = openIdParams,
            nonce = nonce
        )
    }

    fun extractOpenIdParamsFromURL(url: String): PostResponseOpenIdParamsDSO {

        val params = parseQueryParams(url)

        val ns = params["openid.ns"] ?: error("Could not extract 'openid.ns' from URL")
        val mode = params["openid.mode"] ?: error("Could not extract 'openid.mode' from URL")
        val opEndpoint = params["openid.op_endpoint"] ?: error("Could not extract 'openid.op_endpoint' from URL")
        val claimedId = params["openid.claimed_id"] ?: error("Could not extract 'openid.claimed_id' from URL")
        val identity = params["openid.identity"] ?: error("Could not extract 'openid.identity' from URL")
        val returnTo = params["openid.return_to"] ?: error("Could not extract 'openid.return_to' from URL")
        val responseNonce = params["openid.response_nonce"] ?: error("Could not extract 'openid.response_nonce' from URL")
        val assocHandle = params["openid.assoc_handle"] ?: error("Could not extract 'openid.assoc_handle' from URL")
        val signed = params["openid.signed"] ?: error("Could not extract 'openid.signed' from URL")
        val sig = params["openid.sig"] ?: error("Could not extract 'openid.sig' from URL")

        return PostResponseOpenIdParamsDSO(
            ns = ns,
            mode = mode,
            opEndpoint = opEndpoint,
            claimedId = claimedId,
            identity = identity,
            returnTo = returnTo,
            responseNonce = responseNonce,
            assocHandle = assocHandle,
            signed = signed,
            sig = sig
        )
    }

    private fun parseQueryParams(url: String): Map<String, String> {

        val queryString = url.substringAfter("?", "")
        if (queryString.isEmpty()) return emptyMap()

        return queryString.split("&").mapNotNull { param ->
            val parts = param.split("=", limit = 2)
            if (parts.size == 2) {
                val key = URLDecoder.decode(parts[0], Charsets.UTF_8)
                val value = URLDecoder.decode(parts[1], Charsets.UTF_8)
                key to value
            } else {
                null
            }
        }.toMap()
    }

    private fun extractInputValue(html: String, name: String): String? {
        val regex = """<input[^>]*name=["']$name["'][^>]*value=["']([^"']*)["'][^>]*/?>""".toRegex()
        val matchResult = regex.find(html)
        if (matchResult != null) {
            return matchResult.groupValues[1]
        }

        val regexReversed = """<input[^>]*value=["']([^"']*)["'][^>]*name=["']$name["'][^>]*/?>""".toRegex()
        return regexReversed.find(html)?.groupValues?.get(1)
    }
}
