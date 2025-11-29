package dmitriy.losev.cs

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.openqa.selenium.By
import org.openqa.selenium.WindowType
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable

fun main(): Unit = runBlocking {
    val driver = RandomizedChromeFactory.create(headless = false)
    val tab = driver.switchTo().newWindow(WindowType.TAB)
    tab.get("https://outlook.live.com/mail/0/?prompt=create_account")
    val emailInput = tab.findElement(By.ById("floatingLabelInput5"))
    emailInput.clear()
    emailInput.sendKeys("adfwaedwdwa")
    val buttonNext = driver.findElement(By.xpath("//button[normalize-space(.)='Далее' and @type='submit']"))
    buttonNext.click()
    delay(5000L)
    val password = generatePassword()
    val passwordInput = driver.findElement(By.id("floatingLabelInput11"))
    passwordInput.clear()
    passwordInput.sendKeys(password)
    val passwordButtonNext = tab.findElement(By.cssSelector("button[data-testid='primaryButton'][type='submit']"))
    passwordButtonNext.click()
    delay(5000L)

}