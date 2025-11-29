package dmitriy.losev.cs.di

import com.sun.jna.Native
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.win32.W32APIOptions
import dmitriy.losev.cs.mutex.NtDll
import dmitriy.losev.cs.User32Extended
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
@ComponentScan("dmitriy.losev.cs")
class CoreSystemModule {

    @Singleton
    internal fun getUser32Extended(): User32Extended {
        return Native.load("user32", User32Extended::class.java, W32APIOptions.DEFAULT_OPTIONS) as User32Extended
    }

    @Singleton
    internal fun getNtDll(): NtDll {
        return Native.load("ntdll", NtDll::class.java)
    }

    @Singleton
    internal fun getKernel32(): Kernel32 {
        return Kernel32.INSTANCE
    }
}