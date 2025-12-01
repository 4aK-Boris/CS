package dmitriy.losev.cs

sealed interface Environment {

    val steamPath: String
    val csPath: String
    val csFloatHost: String

    val isProd: Boolean

    object Production : Environment {
        override val steamPath = "C:\\Program Files (x86)\\Steam\\steam.exe"
        override val csPath = "E:\\SteamLibrary\\steamapps\\common\\Counter-Strike Global Offensive"
        override val csFloatHost = "localhost"
        override val isProd = true
    }

    object Develompment : Environment {
        override val steamPath = "C:\\Program Files (x86)\\Steam\\steam.exe"
        override val csPath = "E:\\SteamLibrary\\steamapps\\common\\Counter-Strike Global Offensive"
        override val csFloatHost = "localhost"
        override val isProd = false
    }
}
