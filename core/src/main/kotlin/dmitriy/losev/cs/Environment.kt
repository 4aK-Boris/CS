package dmitriy.losev.cs

sealed interface Environment {

    val mobileProxyHost: String

    val isProd: Boolean

    object Production : Environment {
        override val mobileProxyHost = "127.0.0.1"
        override val isProd = true
    }

    object Development : Environment {
        override val mobileProxyHost = "192.168.1.29"
        override val isProd = false
    }
}
