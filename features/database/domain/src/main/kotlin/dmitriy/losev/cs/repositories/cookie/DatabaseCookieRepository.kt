package dmitriy.losev.cs.repositories.cookie

import dmitriy.losev.cs.dto.cookie.NetworkCookieDTO

interface DatabaseCookieRepository {

    suspend fun saveCookies(cookies: List<NetworkCookieDTO>)
}
