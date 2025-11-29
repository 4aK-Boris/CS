package dmitriy.losev.cs.mutex

import com.sun.jna.Pointer
import com.sun.jna.Structure

@Structure.FieldOrder("length", "maximumLength", "buffer")
class UnicodeString : Structure {
    @JvmField var length: Short = 0
    @JvmField var maximumLength: Short = 0
    @JvmField var buffer: Pointer? = null

    constructor() : super()

    constructor(p: Pointer) : super(p) {
        read()
    }

    fun getString(): String? {
        return try {

            if (buffer == null || length <= 0) {
                return null
            }

            val maxLength = length.toInt()

            if (maxLength > 32768) {
                return null
            }

            buffer?.getWideString(0)?.take(n = maxLength / 2)
        } catch (e: Exception) {
            null
        }
    }
}