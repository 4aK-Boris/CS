package dmitriy.losev.cs

import dmitriy.losev.cs.ProfileRandomizer
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.devtools.DevTools
import org.openqa.selenium.devtools.HasDevTools

// поменяй v126 на актуальную
import org.openqa.selenium.devtools.v140.network.Network
import org.openqa.selenium.devtools.v140.emulation.Emulation
import org.openqa.selenium.devtools.v140.page.Page

import java.util.Optional

object RandomizedChromeFactory {

    fun create(headless: Boolean = false): WebDriver {
        val profile = ProfileRandomizer.random()

        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions().apply {
            if (headless) addArguments("--headless=new")
            addArguments("--disable-gpu")
            addArguments("--no-sandbox", "--disable-dev-shm-usage")
            addArguments("--window-size=${profile.viewport.first},${profile.viewport.second}")
            addArguments("--lang=${profile.primaryLang}")
            addArguments("--user-agent=${profile.userAgent}")

            // Геолокация = блок (2) => сайт увидит «запрещено»
            setExperimentalOption("prefs", mapOf(
                // эти два ключа надёжно гасят промпт/доступ
                "profile.default_content_setting_values.geolocation" to 2,
                "profile.managed_default_content_settings.geolocation" to 2
            ))
        }

        val driver = ChromeDriver(options)
        val devTools: DevTools = (driver as HasDevTools).devTools
        devTools.createSession()

        // 1) Network: UA/Accept-Language/Platform
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
        devTools.send(
            Network.setUserAgentOverride(
                profile.userAgent,
                Optional.of(profile.acceptLanguage),
                Optional.of(profile.platform),
                Optional.empty() // можно задать client hints metadata
            )
        )

        // 2) Часовой пояс / язык локали / метрики устройства
        devTools.send(Emulation.setTimezoneOverride(profile.timezone))
        devTools.send(Emulation.setLocaleOverride(Optional.of(profile.primaryLang)))
        devTools.send(
            Emulation.setDeviceMetricsOverride(
                profile.viewport.first,
                profile.viewport.second,
                profile.deviceScaleFactor,
                false, // mobile?
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            )
        )

        // 3) JS-инъекция до загрузки страниц: подкручиваем navigator.*
        // + гарантированно "запрещаем" геолокацию на уровне JS-API
        val js = """
            (function() {
              const hw = ${profile.hardwareConcurrency};
              const mem = ${profile.deviceMemory};
              const lang = "${profile.primaryLang}";
              const langs = "${profile.acceptLanguage}".split(',').map(s => s.split(';')[0]);
              const platform = "${profile.navigatorPlatform}";
              
              try { Object.defineProperty(navigator, 'hardwareConcurrency', { get: () => hw }); } catch(e){}
              try { Object.defineProperty(navigator, 'deviceMemory', { get: () => mem }); } catch(e){}
              try { Object.defineProperty(navigator, 'language', { get: () => lang }); } catch(e){}
              try { Object.defineProperty(navigator, 'languages', { get: () => langs }); } catch(e){}
              try { Object.defineProperty(navigator, 'platform', { get: () => platform }); } catch(e){}
              try { Object.defineProperty(navigator, 'webdriver', { get: () => false }); } catch(e){}

              // геолокация «отключена» — всегда Permission denied
              const geoErr = { code: 1, message: 'User denied Geolocation' };
              const geo = {
                getCurrentPosition: function(success, error) { if (error) error(geoErr); },
                watchPosition: function(success, error) { if (error) error(geoErr); return Math.floor(Math.random()*1e6); },
                clearWatch: function() {}
              };
              try { Object.defineProperty(navigator, 'geolocation', { get: () => geo }); } catch(e){}
            })();
        """.trimIndent()
        devTools.send(
            Page.addScriptToEvaluateOnNewDocument(
                js,
                /* worldName */ Optional.empty(),          // или Optional.of("selenium") для изолированного мира
                /* includeCommandLineAPI */ Optional.of(false),
                /* runImmediately */ Optional.of(true)     // выполнить немедленно для уже открытой страницы (если возможно)
            )
        )

        return driver
    }
}
