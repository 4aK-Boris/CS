package dmitriy.losev.cs.mutex

import com.sun.jna.Pointer
import com.sun.jna.Structure

@Structure.FieldOrder("processId", "objectTypeNumber", "flags", "handle", "pObject", "grantedAccess")
internal class SystemHandleInformation : Structure {
    @JvmField
    var processId: Int = 0

    @JvmField

    var objectTypeNumber: Byte = 0

    @JvmField
    var flags: Byte = 0

    @JvmField
    var handle: Short = 0

    @JvmField
    var pObject: Pointer? = null

    @JvmField
    var grantedAccess: Int = 0

    constructor(p: Pointer) : super(p) {
        read()
    }
}