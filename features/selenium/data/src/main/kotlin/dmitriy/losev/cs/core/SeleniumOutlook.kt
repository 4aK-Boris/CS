package dmitriy.losev.cs.core

import kotlinx.coroutines.delay
import org.koin.core.annotation.Singleton
import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.WindowType
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated

@Singleton
class SeleniumOutlook(private val selenium: Selenium) {

    private lateinit var driver: WebDriver
    private lateinit var creationEmailDriver: WebDriver

    fun setWebDriver() {
        driver = selenium.createWebDriver()
    }

    fun openOutlookSiteForCreateAccount() {
        creationEmailDriver = driver
            .switchTo()
            .newWindow(WindowType.TAB)
            .apply { get(OUTLOOK_FOR_CREATION_ACCOUNT_URL) }
    }

    suspend fun inputEmailPrefixAndGoNext(emailPrefix: String) {

        val waitDriver = selenium.getWaitWebdriver(driver = creationEmailDriver, timeout = 10L)

        waitDriver.until(elementToBeClickable(By.ById("floatingLabelInput5"))).apply {
            clear()
            sendKeys(emailPrefix)
        }

        delay(2000L)

        waitDriver.until(elementToBeClickable(By.cssSelector("button[data-testid='primaryButton'][type='submit']"))).apply {
            click()
        }
    }

    suspend fun enterPasswordAndGoNext(password: String) {

        val waitDriver = selenium.getWaitWebdriver(driver = creationEmailDriver, timeout = 10L)

        waitDriver.until(elementToBeClickable(By.id("floatingLabelInput11"))).apply {
            clear()
            sendKeys(password)
        }

        delay(2000L)

        waitDriver.until(elementToBeClickable(By.cssSelector("button[data-testid='primaryButton'][type='submit']"))).apply {
            click()
        }
    }

    suspend fun setRegionAndDate(
        month: Month,
        day: Int,
        year: Int
    ) {
        val birthDayLocator = By.cssSelector("button#BirthDayDropdown[role='combobox']")
        val birthYearLocator = By.cssSelector("input[type='number'][name='BirthYear'], input#floatingLabelInput21")
        val buttonNextLocator = By.cssSelector("button[data-testid='primaryButton'][type='submit']")
        val waitDriver = selenium.getWaitWebdriver(creationEmailDriver)

        setCountryRU()

        delay(2000L)

        setBirthMonth(month)

        delay(2000L)

        selectFromCombo(comboLocator = birthDayLocator, optionText = day.toString())

        delay(2000L)

        waitDriver.until(elementToBeClickable(birthYearLocator)).apply {
            clear()
            sendKeys(year.toString())
        }

        delay(2000L)

        waitDriver.until(elementToBeClickable(buttonNextLocator)).apply {
            click()
        }
    }

    suspend fun setFirstAndLastName(firstName: String, lastName: String) {

        val waitDriver = selenium.getWaitWebdriver(driver = creationEmailDriver)

        val firstNameLocator = By.id("firstNameInput")
        val lastNameLocator = By.id("lastNameInput")
        val buttonNextLocator = By.cssSelector("button[data-testid='primaryButton'][type='submit']")

        waitDriver.until(visibilityOfElementLocated(firstNameLocator)).apply {
            clear()
            sendKeys(firstName)
        }

        delay(2000L)

        waitDriver.until(visibilityOfElementLocated(lastNameLocator)).apply {
            clear()
            sendKeys(lastName)
        }

        delay(2000L)

        waitDriver.until(elementToBeClickable(buttonNextLocator)).apply {
            click()
        }
    }

    fun setBirthMonth(month: Month, timeout: Long = 10) {

        val waitDriver = selenium.getWaitWebdriver(creationEmailDriver, timeout)

        val comboL = By.cssSelector("button#BirthMonthDropdown[role='combobox']")
        val js = creationEmailDriver as JavascriptExecutor

        var combo = waitDriver.until(elementToBeClickable(comboL))

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", combo)

        try { combo.click() } catch (_: Exception) { js.executeScript("arguments[0].click()", combo) }

        waitDriver.until { drv ->
            try {
                val c = drv.findElement(comboL)
                c.getAttribute("aria-expanded") == "true"
            } catch (_: StaleElementReferenceException) { false }
        }
        combo = creationEmailDriver.findElement(comboL) // перехватить возможный stale

        val list = run {
            val ctrl = combo.getAttribute("aria-controls")
            if (!ctrl.isNullOrBlank()) {
                waitDriver.until(presenceOfElementLocated(By.id(ctrl)))
            } else {
                waitDriver.until(visibilityOfElementLocated(By.cssSelector("[role='listbox']")))
            }
        }

        val two = month.toString().padStart(2, '0')

        // 3a) по data-* атрибутам (самый стабильный вариант)
        val cssCandidates = listOf(
            "[role='option'][data-value='$month']",
            "[role='option'][data-value='$two']",
            "[role='option'][value='$month']",
            "[role='option'][data-month='$month']"
        )
        var option: WebElement? = null
        for (css in cssCandidates) {
            val found = list.findElements(By.cssSelector(css)).filter { it.isDisplayed }
            if (found.isNotEmpty()) { option = found.first(); break }
        }

        if (option == null) {
            val x = ".//*[contains(@role,'option')][normalize-space(.)='%s']"
            val textXpaths = listOf(month.en, month.enShort, month.ru).map { String.format(x, it) }
            for (xp in textXpaths) {
                val found = list.findElements(By.xpath(xp)).filter { it.isDisplayed }
                if (found.isNotEmpty()) { option = found.first(); break }
            }
        }

        if (option == null) {

            list.sendKeys(Keys.HOME)
            repeat(month.ordinal - 1) { list.sendKeys(Keys.ARROW_DOWN) }
            list.sendKeys(Keys.ENTER)
        } else {
            js.executeScript("arguments[0].scrollIntoView({block:'nearest'});", option)
            try { option.click() } catch (_: Exception) { js.executeScript("arguments[0].click()", option) }
        }

        // 4) проверить, что значение действительно установилось (кнопка часто перерисовывается)
        waitDriver.until {
            val btn = creationEmailDriver.findElement(comboL)
            val v = btn.getAttribute("value") ?: ""
            val t = btn.findElement(By.cssSelector("[data-testid='truncatedSelectedText']")).text.trim()
            v == month.toString() || v == two ||
                    t.equals(month.en, true) || t.equals(month.enShort, true) || t.equals(month.ru, true)
        }
    }

    private fun selectFromCombo(comboLocator: By, optionText: String, timeout: Long = 10) {

        val waitDriver = selenium.getWaitWebdriver(creationEmailDriver, timeout)

        val combo = waitDriver.until(elementToBeClickable(comboLocator))

        val current = combo.findElement(By.cssSelector("[data-testid='truncatedSelectedText']")).text.trim()

        if (current == optionText) return

        combo.click()

        waitDriver.until(attributeToBe(combo, "aria-expanded", "true"))


        val opt = waitDriver.until(
            visibilityOfElementLocated(
                By.xpath(
                    "//*[(@role='listbox' or @role='list')]" + "//*[contains(@role,'option')][normalize-space(.)=${
                        "'" + optionText.replace(oldValue = "'", newValue = "\\'") + "'"
                    }]"
                )
            )
        )

        try {
            opt.click()
        } catch (_: ElementClickInterceptedException) {
            (this as JavascriptExecutor).executeScript("arguments[0].click()", opt)
        }
    }

    private fun setCountryRU(timeout: Long = 10) {

        val wait = selenium.getWaitWebdriver(driver = creationEmailDriver, timeout)

        val comboLocator = By.cssSelector("button[data-testid='countryDropdown'][role='combobox']")

        var combo = wait.until(elementToBeClickable(comboLocator))

        if (combo.getAttribute("value") == "RU") return

        combo.click()

        wait.until(attributeToBe(combo, "aria-expanded", "true"))

        val codeSelectors = listOf(
            "[role='listbox'] [role='option'][data-value='RU']",
            "[role='listbox'] [role='option'][data-code='RU']",
            "[role='option'][data-value='RU']",
            "[role='option'][data-code='RU']"
        )
        var option: WebElement? = null

        for (sel in codeSelectors) {
            val found = creationEmailDriver.findElements(By.cssSelector(sel))
            if (found.isNotEmpty()) {
                option = found.first(); break
            }
        }

        if (option == null) {
            option = wait.until(
                visibilityOfElementLocated(
                    By.xpath(
                        "//*[(@role='listbox' or @role='list')]//*[contains(@role,'option')]" +
                                "[normalize-space(.)='Russia' or normalize-space(.)='Россия']"
                    )
                )
            )
        }

        try {
            option!!.click()
        } catch (_: ElementClickInterceptedException) {
            (this as JavascriptExecutor).executeScript("arguments[0].click()", option)
        }

        wait.until {
            combo = creationEmailDriver.findElement(comboLocator)
            val codeOk = combo.getAttribute("value") == "RU"
            val txt = combo.findElement(By.cssSelector("[data-testid='truncatedSelectedText']")).text.trim()
            codeOk || txt.equals("Russia", true) || txt.equals("Россия", true)
        }
    }

    companion object {
        private const val OUTLOOK_FOR_CREATION_ACCOUNT_URL = "https://outlook.live.com/mail/0/?prompt=create_account"
    }
}