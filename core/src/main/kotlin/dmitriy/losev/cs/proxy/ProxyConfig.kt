package dmitriy.losev.cs.proxy

interface ProxyConfig {
    val host: String

    val port: Int

    val login: String

    val password: String

    val name: String
        get() = "$host:$port"

    data class Default(
        override val host: String,
        override val port: Int,
        override val login: String,
        override val password: String
    ) : ProxyConfig
}
