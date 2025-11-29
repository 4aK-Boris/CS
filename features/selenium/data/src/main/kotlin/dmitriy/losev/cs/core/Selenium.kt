package dmitriy.losev.cs.core

import java.time.Duration
import org.koin.core.annotation.Singleton
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

@Singleton
class Selenium {

    fun createWebDriver(): WebDriver {
        return RandomizedChromeFactory.create(headless = false)
    }

    fun getWaitWebdriver(driver: WebDriver, timeout: Long = 10): WebDriverWait {
        return WebDriverWait(driver, Duration.ofSeconds(timeout))
    }
}