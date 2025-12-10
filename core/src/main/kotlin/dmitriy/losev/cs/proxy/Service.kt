package dmitriy.losev.cs.proxy

enum class Service(val host: String) {
    CS_MARKET(host = "steamcommunity.com/market"),
    CS_FLOAT(host = "csfloat.com"),
    LIS_SKINS(host = "api.lis-skins.com"),
    PULSE(host = "api-pulse.tradeon.space/api"),
    MARKET_CSGO(host = "market.csgo.com"),
    STEAM_POWERED(host = "api.steampowered.com"),
    STEAM_COMMUNITY(host = "steamcommunity.com"),
    PROD_MOBILE_PROXY_SERVICE(host = "192.168.1.29:8090"),
    DEV_MOBILE_PROXY_SERVICE(host = "192.168.1.29:8090");
}
