package dmitriy.losev.cs.proxy

enum class Service(val host: String) {
    CS_MARKET(host = "steamcommunity.com/market"),
    CS_FLOAT(host = "localhost"),
    LIS_SKINS(host = "api.lis-skins.com"),
    PULSE(host = "api-pulse.tradeon.space/api"),
    MARKET_CSGO(host = "market.csgo.com"),
    STEAM_MOBILE(host = "api.steampowered.com");
}