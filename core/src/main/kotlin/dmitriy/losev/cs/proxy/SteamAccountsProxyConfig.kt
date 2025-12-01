package dmitriy.losev.cs.proxy

data class SteamAccountsProxyConfig(
    val steamId: ULong,
    val proxyConfig: ProxyConfig
): ProxyConfig by proxyConfig
