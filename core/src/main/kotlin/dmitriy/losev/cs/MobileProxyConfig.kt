package dmitriy.losev.cs

data class MobileProxyConfig(
    val host: String = EMPTY_STRING,
    val mobileProxyDeviceConfigs: List<MobileProxyDeviceConfig> = emptyList()
)
