package dmitriy.losev.cs.exceptions

class ValidationException(val errors: List<String>) : Exception(errors.joinToString("; "))
