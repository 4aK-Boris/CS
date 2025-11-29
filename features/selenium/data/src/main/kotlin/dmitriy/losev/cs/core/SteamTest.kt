package dmitriy.losev.cs.core

import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val steamCode = SteamCode()

private val logins = File("feature/selenium/data/src/main/resources/logins.txt")
    .readLines()
    .map(transform = String::trim)

private val passwords = File("feature/selenium/data/src/main/resources/passwords.txt")
    .readLines()
    .map(transform = String::trim)

private const val PATH = "E:\\ASF-win-x64\\config\\"


fun main(): Unit = runBlocking {

    logins.zip(other = passwords).forEach { (login, password) ->

        val selenium = Selenium()

        val steam = SeleniumSteam(selenium)

        try {

            val code = steamCode.generateCode(filePath = "${PATH}$login.maFile")

            steam.setWebDriver()

            steam.openSteamForLoginInAccount()

            steam.setLoginAndPassword(login = login, password = password)

            delay(2000L)

            val profileUrl = steam.setCode(code = code)

            delay(5000L)

            steam.addGames()

            steam.addGroups()

            steam.addFriends(profileUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            steam.quit()

            delay(300_000L)
        }
    }

}