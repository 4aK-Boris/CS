package dmitriy.losev.cs.core

import kotlinx.coroutines.delay
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WindowType
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import org.openqa.selenium.support.ui.Select
import kotlin.random.Random

class SeleniumSteam(private val selenium: Selenium) {

    private lateinit var driver: WebDriver
    private lateinit var accountDriver: WebDriver

    fun setWebDriver() {
        driver = selenium.createWebDriver()
    }

    fun quit() {
        driver.quit()
    }

    fun openSteamForLoginInAccount() {
        accountDriver = driver
            .switchTo()
            .newWindow(WindowType.TAB)
            .apply { get(ENTER_URL) }
    }

    suspend fun setLoginAndPassword(login: String, password: String) {

        val waitDriver = selenium.getWaitWebdriver(driver = accountDriver)

        val inputFormLocator = By.xpath("//form[.//input[@type='password'] and .//button[@type='submit']]")
        val loginLocator = By.cssSelector("input[type='text']")
        val passwordLocator = By.cssSelector("input[type='password']")
        val buttonNextLocator = By.className("DjSvCZoKKfoNSmarsEcTS")

        val inputForm = waitDriver.until(presenceOfElementLocated(inputFormLocator))

        val loginInput = waitDriver.until { _ ->
            inputForm.findElements(loginLocator).firstOrNull { it.isDisplayed && it.isEnabled }
        } ?: throw NoSuchElementException("login input not found")

        val passwordInput = waitDriver.until { _ ->
            inputForm.findElements(passwordLocator).firstOrNull { it.isDisplayed && it.isEnabled }
        } ?: throw NoSuchElementException("password input not found")

        val buttonNext = waitDriver.until(
            elementToBeClickable(
                inputForm.findElement(buttonNextLocator)
            )
        )

        loginInput.clear()
        passwordInput.clear()

        loginInput.sendKeys(login)
        passwordInput.sendKeys(password)

        delay(2000L)

        buttonNext.click()
    }

    suspend fun setCode(code: String): String {

        val waitDriver = selenium.getWaitWebdriver(accountDriver, timeout = 60)

        val inputTextLocator = By.className("_3xcXqLVteTNHmk-gh9W65d")
        val profileLocator = By.className("user_avatar")

        driver
            .findElements(inputTextLocator)
            .zip(other = code.toList())
            .forEach { (input, char) ->
                println(char)
                delay(1000L)
                input.clear()
                input.sendKeys(char.toString())
            }

        delay(5000L)

        return waitDriver.until(visibilityOfElementLocated(profileLocator)).getAttribute("href") ?: throw Exception()
    }

    suspend fun addGames() {

        gameIds.forEach { gameId ->

            delay(2000L)

            addGameInLibrary(gameId)
        }
    }


    private suspend fun addGameInLibrary(gameId: Int) {

        val gameDriver = accountDriver
            .switchTo()
            .newWindow(WindowType.TAB)
            .apply { get("$GAME_URL$gameId") }

        try {

            setAge(gameDriver)

            val waitDriver = selenium.getWaitWebdriver(driver = gameDriver)

            val addGameToLibraryLocator = By.cssSelector("span.btn_blue_steamui.btn_medium[role='button']")
            val okButtonLocator = By.className("btn_grey_steamui")

            delay(2000L)

            waitDriver.until(elementToBeClickable(addGameToLibraryLocator)).apply {
                click()
            }

            delay(5000L)

            waitDriver.until(elementToBeClickable(okButtonLocator)).apply {
                click()
            }

            delay(2000L)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            gameDriver.close()
            accountDriver.switchTo().window(accountDriver.windowHandles.last())
        }
    }

    suspend fun addGroups() {

        groups
            .shuffled()
            .take(n = 15)
            .forEach { group ->

                delay(2000L)

                addGroup(groupName = group)
            }
    }

    private suspend fun addGroup(groupName: String) {

        val waitDriver = selenium.getWaitWebdriver(accountDriver)

        val groupDriver = accountDriver
            .switchTo()
            .newWindow(WindowType.TAB)
            .apply { get("$GROUPS_URL$groupName") }

        val addButtonLocator = By.className("btn_green_white_innerfade")

        try {

            delay(2000L)

            waitDriver.until(elementToBeClickable(addButtonLocator)).apply {
                click()
            }

            delay(5000L)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            groupDriver.close()
            accountDriver.switchTo().window(accountDriver.windowHandles.last())
        }
    }

    suspend fun addFriends(profileUrl: String) {

        val friendDriver = accountDriver
            .switchTo()
            .newWindow(WindowType.TAB)
            .apply { get(FRIENDS_URL) }

        val friends = getFriends(profileUrl)

        val pageButtonLocator = By.className("commentthread_pagelink")
        val urlLocator = By.cssSelector("a.commentthread_author_link[href*='steamcommunity.com/']")

        val maxPage = driver.findElements(pageButtonLocator).mapNotNull { pageButton -> pageButton.text.toIntOrNull() }.max()

        val urls = mutableListOf<String>()

        repeat(times = 10) { i ->

            friendDriver.get("$FRIENDS_URL?ctp=${maxPage - i}")

            delay(2000L)

            val data = driver.findElements(urlLocator)
                .filter { it.isDisplayed }
                .mapNotNull { it.getAttribute("href") }

            urls.addAll(data)
        }

        urls
            .distinct()
            .minus(elements = friends.toSet())
            .takeLast(n = 15)
            .forEach { url -> addFriend(url) }
    }

    private suspend fun addFriend(url: String) {

        val waitDriver = selenium.getWaitWebdriver(accountDriver)

        val friendDriver = accountDriver
            .switchTo()
            .newWindow(WindowType.TAB)
            .apply { get(url) }

        try {

            val addFriendButtonLocator = By.className("btn_profile_action")
            val okButtonLocator = By.className("btn_grey_steamui")

            waitDriver.until(elementToBeClickable(addFriendButtonLocator)).apply {
                click()
            }

            delay(5000L)

            waitDriver.until(elementToBeClickable(okButtonLocator)).apply {
                click()
            }

            delay(2000L)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            friendDriver.close()
            accountDriver.switchTo().window(accountDriver.windowHandles.last())
        }
    }

    private fun getFriends(profileUrl: String): List<String> {

        val friendsDriver = accountDriver
            .switchTo()
            .newWindow(WindowType.TAB)
            .apply { get("$profileUrl/friends") }

        val friendLocator = By.className("selectable_overlay")

        val friends = friendsDriver
            .findElements(friendLocator)
            .mapNotNull { element -> element.getAttribute("href") }

        friendsDriver.close()

        accountDriver.switchTo().window(accountDriver.windowHandles.last())

        return friends
    }

    private suspend fun setAge(driver: WebDriver) {

        try {

            val waitDriver = selenium.getWaitWebdriver(driver)

            val daySelectLocator = By.id("ageDay")
            val monthSelectLocator = By.id("ageMonth")
            val yearSelectLocator = By.id("ageYear")
            val buttonOpenLocator = By.id("view_product_page_btn")

            val daySelectElement = waitDriver.until(elementToBeClickable(daySelectLocator))
            val monthSelectElement = waitDriver.until(elementToBeClickable(monthSelectLocator))
            val yearSelectElement = waitDriver.until(elementToBeClickable(yearSelectLocator))
            val buttonOpenElement = waitDriver.until(elementToBeClickable(buttonOpenLocator))

            val daySelect = Select(daySelectElement)
            val monthSelect = Select(monthSelectElement)
            val yearSelect = Select(yearSelectElement)

            daySelect.selectByIndex(Random.nextInt(from = 1, until = 28))
            monthSelect.selectByIndex(Random.nextInt(from = 1, until = 12))
            yearSelect.selectByIndex(Random.nextInt(from = 50, until = 105))

            buttonOpenElement.click()

            delay(3000L)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val ENTER_URL = "https://store.steampowered.com/login"
        private const val GAME_URL = "https://store.steampowered.com/app/"
        private const val FRIENDS_URL = "https://steamcommunity.com/discussions/forum/30/864973577904147767/"

        private const val GROUPS_URL = "https://steamcommunity.com/groups/"

        private val groups = listOf(
            "tradingcards",
            "Natus-Vincere",
            "steamuniverse",
            "familysharing",
            "indiegala",
            "KiFFERSTUEBCHEN",
            "Fnatic",
            "esl",
            "FACEITcom",
            "dota2lounge",
            "240kmPEEK",
            "pcgamer",
            "SteamClientBeta",
            "g2esports-official",
            "eslcs",
            "rustoria",
            "virtusprofessional",
            "steammusic",
            "reddit",
            "warbanditsgg",
            "gmod",
            "SteamDB"
        )

        private val gameIds = listOf(
            230410,
            440,
            570,
            730,
            236390,
            238960,
            291550,
            304930,
            386180,
            386360,
            444090,
            578080,
            700330,
            760160,
            761890,
            918570,
            1085660,
            1172470,
            1549250,
            1568590,
            1974050,
            2073850,
            2074920,
            2357570,
            2437170,
            2923300,
            3127280,
            3626910,
            3697340,
            3766920,
            3840970,
            3868440
        )
    }
}