package dmitriy.losev.cs

object SystemProperties {

    private const val USER_HOME_KEY = "user.home"
    private const val USER_DIRECTORY_KEY = "user.dir"
    private const val OS_NAME_KEY = "os.name"
    private const val APPLICATION_DATA_KEY = "APPDATA"


    val userDirectory: String
        get() = System.getProperty(USER_HOME_KEY)

    val osName: String
        get() = System.getProperty(OS_NAME_KEY)

    val applicationData: String
        get() = System.getProperty(APPLICATION_DATA_KEY)

    val applicationDirectory: String
        get() = System.getProperty(USER_DIRECTORY_KEY)
}