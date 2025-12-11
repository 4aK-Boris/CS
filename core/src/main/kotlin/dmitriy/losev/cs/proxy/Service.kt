package dmitriy.losev.cs.proxy

sealed interface Service {
    val host: String

    data object CsMarket : Service {
        override val host = "steamcommunity.com/market"
    }

    data object CsFloat : Service {
        override val host = "csfloat.com"
    }

    data object LisSkins : Service {
        override val host = "api.lis-skins.com"
    }

    data object Pulse : Service {
        override val host = "api-pulse.tradeon.space/api"
    }

    data object MarketCsgo : Service {
        override val host = "market.csgo.com"
    }

    data object SteamPowered : Service {
        override val host = "api.steampowered.com"
    }

    data object SteamCommunity : Service {
        override val host = "steamcommunity.com"
    }

    data class MobileProxy(override val host: String) : Service
}
