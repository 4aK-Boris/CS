package dmitriy.losev.cs

import io.github.smiley4.ktoropenapi.config.RouteConfig

abstract class BaseDescription(private val isProd: Boolean) {

    protected fun RouteConfig.auth() {
        if (isProd) {
            securitySchemeNames(SECURITY_SCHEME)
        }
    }

    companion object {
        private const val SECURITY_SCHEME = "BasicAuth"
    }
}
