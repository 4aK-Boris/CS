package dmitriy.losev.cs.exceptions

abstract class ProgramBaseException(errorCode: Int, override val message: String? = null) : BaseException(errorCode, message)
