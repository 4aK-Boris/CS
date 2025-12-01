package dmitriy.losev.cs.exceptions

abstract class CSPanelException(open val errorCode: Int, override val message: String? = null, override val cause: Throwable? = null) : Exception() {

    override fun hashCode(): Int {
        return errorCode
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CSPanelException

        return errorCode == other.errorCode
    }
}
