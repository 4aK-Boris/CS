package dmitriy.losev.cs

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.ksp.generated.module
import org.koin.test.verify.verify
import kotlin.test.Test

class AppModuleTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKoinModule() {
        AppModule().module.verify(extraTypes = listOf(Context::class))
    }
}