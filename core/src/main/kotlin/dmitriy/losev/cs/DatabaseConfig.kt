package dmitriy.losev.cs

data class DatabaseConfig(
    val databaseUrl: String = EMPTY_STRING,
    val databaseUser: String = EMPTY_STRING,
    val databasePassword: String = EMPTY_STRING
)
