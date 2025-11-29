package dmitriy.losev.cs

import java.io.PrintStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

object ConsoleEncodingFix {

    private var isFixed = false

    fun fix() {
        if (isFixed) return

        try {
            when {
                isWindows() -> fixWindows()
                else -> fixUnix()
            }
            isFixed = true
            println("✓ Кодировка консоли установлена: UTF-8")
        } catch (e: Exception) {
            System.err.println("⚠ Не удалось установить UTF-8: ${e.message}")
        }
    }

    private fun isWindows(): Boolean {
        return System.getProperty("os.name").contains("Windows", ignoreCase = true)
    }

    private fun fixWindows() {
        // 1. Устанавливаем кодовую страницу консоли на UTF-8
        try {
            ProcessBuilder("cmd", "/c", "chcp", "65001")
                .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                .redirectError(ProcessBuilder.Redirect.DISCARD)
                .start()
                .waitFor()
        } catch (e: Exception) {
            // Игнорируем ошибки, попробуем через PowerShell
            try {
                ProcessBuilder(
                    "powershell", "-Command",
                    "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8"
                ).start().waitFor()
            } catch (e2: Exception) {
                // Ничего не делаем
            }
        }

        // 2. Устанавливаем UTF-8 для System.out и System.err
        System.setOut(PrintStream(System.out, true, StandardCharsets.UTF_8))
        System.setErr(PrintStream(System.err, true, StandardCharsets.UTF_8))

        // 3. Устанавливаем системное свойство
        System.setProperty("file.encoding", "UTF-8")
        System.setProperty("console.encoding", "UTF-8")

        // 4. Принудительно устанавливаем дефолтную кодировку (хак)
        try {
            val charset = Charset::class.java.getDeclaredField("defaultCharset")
            charset.isAccessible = true
            charset.set(null, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            // Игнорируем - работает не на всех версиях Java
        }
    }

    private fun fixUnix() {
        System.setOut(PrintStream(System.out, true, StandardCharsets.UTF_8))
        System.setErr(PrintStream(System.err, true, StandardCharsets.UTF_8))
        System.setProperty("file.encoding", "UTF-8")
    }
}