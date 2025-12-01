package dmitriy.losev.cs.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

private val selenium = Selenium()
private val outlook = SeleniumOutlook(selenium)

private const val EMAIL_PREFIX = "anastasmarkova2003"
private const val PASSWORD = "2321ijnyx4cy8324$$$$"
private const val DAY = 14
private const val YEAR = 2003
private val MONTH = Month.April

private const val FIRST_NAME = "Анастасия"
private const val LAST_NAME = "Маркова"

fun main(): Unit = runBlocking {

    outlook.setWebDriver()

    outlook.openOutlookSiteForCreateAccount()

    outlook.inputEmailPrefixAndGoNext(emailPrefix = EMAIL_PREFIX)
    outlook.enterPasswordAndGoNext(password = PASSWORD)

    delay(2000L)

    outlook.setRegionAndDate(month = MONTH, year = YEAR, day = DAY)

    delay(2000L)

    outlook.setFirstAndLastName(firstName = FIRST_NAME, lastName = LAST_NAME)

    delay(2000L)
}