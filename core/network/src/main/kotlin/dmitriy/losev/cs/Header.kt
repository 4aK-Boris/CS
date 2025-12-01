package dmitriy.losev.cs

import io.ktor.http.HttpHeaders

sealed class Header(val key: String) {

    object CacheControl : Header(key = HttpHeaders.CacheControl) {
        const val MAX_AGE_ZERO = "max-age=0"
    }

    object SecChUa : Header(key = "sec-ch-ua") {
        const val GOOGLE_CHROME =
            "\"Google Chrome\";v=\"135\", \"Not-A.Brand\";v=\"8\", \"Chromium\";v=\"135\"pair_a501db0f161c45a29d4edb6f598bc9a3"
    }

    object SecChUaMobile : Header(key = "sec-ch-ua-mobile") {
        const val VALUE = "?0"
    }

    object SecChUaPlatform : Header(key = "sec-ch-ua-platform") {
        const val MAC_OS = "\"macOS\""
    }

    object UpgradeInsecureRequests : Header(key = "Upgrade-Insecure-Requests") {
        const val VALUE = "1"
    }

    object UserAgent : Header(key = HttpHeaders.UserAgent) {
        const val MAC_OS =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36"
    }

    object Accept : Header(key = HttpHeaders.Accept) {
        const val TEXT_AND_HTML =
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
        const val VALUE = "*/*"
    }

    object SecFetchSite : Header(key = "Sec-Fetch-Site") {
        const val SAME_ORIGIN = "same-origin"
        const val SAME_SITE = "same-site"
    }

    object SecFetchMode : Header(key = "Sec-Fetch-Mode") {
        const val NAVIGATE = "navigate"
        const val CORS = "cors"
    }

    object SecFetchUser : Header(key = "Sec-Fetch-User") {
        const val VALUE = "?1"
        const val FRAME = "iframe"
    }

    object SecFetchDest : Header(key = "Sec-Fetch-Dest") {
        const val DOCUMENT = "document"
        const val EMPTY = "empty"
        const val FRAME = "iframe"
    }

    object AcceptLanguage : Header(key = HttpHeaders.AcceptLanguage) {
        const val VALUE = "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7"
    }

    object AcceptEncoding : Header(key = HttpHeaders.AcceptEncoding) {
        const val VALUE = "gzip, deflate"
    }

    object Referrer : Header(key = HttpHeaders.Referrer) {
        const val SSO = "https://sso.o3.ru/"
    }

    object Origin : Header(key = HttpHeaders.Origin) {
        const val NULL_VALUE = "null"
        const val SSO = "https://sso.o3.ru"
    }

    object Host : Header(key = HttpHeaders.Host) {
        const val SSO = "sso.o3.ru"
    }

    object Connection : Header(key = HttpHeaders.Connection) {
        const val KEEP_ALIVE = "keep-alive"
    }

    object Location: Header(key = HttpHeaders.Location)

    object AppVersion: Header(key = "X-O3-App-Version") {
        const val SUPPORT_ADMIN = "release/LSSUP-5715"
    }

    object AppName: Header(key = "X-O3-App-Name") {
        const val SUPPORT_ADMIN_GRPC = "supportadmin-grpc"
        const val POISON = "ls_wh_support_team"
    }

    object UserId: Header(key = "X-O3-User-Id") {
        const val ME = "dmloseva"
    }

    object UserSource: Header(key = "X-O3-User-Source") {
        const val KEY_CLOCK = "KeyClock"
        const val LOZON = "USER_SOURCE_LOZON"
    }

    object ContentType: Header(key = "Content-Type")
}
