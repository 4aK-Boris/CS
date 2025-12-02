package dmitriy.losev.cs.proxy

data class SteamAccountProxyConfig(
    val steamId: Long,
    val proxyConfig: ProxyConfig
): ProxyConfig by proxyConfig
