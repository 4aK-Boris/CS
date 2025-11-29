package dmitriy.losev.cs.core

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.SteamConfig
import dmitriy.losev.cs.process.ProcessHandler
import dmitriy.losev.cs.window.SteamWindowHandler
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton
internal class SteamLauncher(
    @Provided private val context: Context,
    @Provided private val processHandler: ProcessHandler,
    @Provided private val steamWindowHandler: SteamWindowHandler
) {

    fun createSteamProcess(userName: String): Long {

        val command = context.environment.steamPath

        val params = buildList {
            add("-nofriendsui")
            add("-vgui")
            add("-noreactlogin")
            add("-noverifyfiles")
            add("-nobootstrapupdate")
            add("-skipinitialbootstrap")
            add("-norepairfiles")
            add("-overridepackageurl")
            add("-disable-winh264")
            add("-language")
            add("english")
            add("-master_ipc_name_override")
            add("cs_$userName")
            add("-applaunch")
            add("730")
            add("+exec")
            add("default_config.cfg")
            add("+exec")
            add("gamestate_integration_config.cfg")
            add("-w")
            add("360")
            add("-h")
            add("270")
            add("-console")
            add("-condebug")
            add("-conclearlog")
            add("-allowmultiple")
            add("-con_logfile")
            add("$userName.log")
            add("-swapcores")
            add("-noqueuedload")
            add("-vrdisable")
            add("-windowed")
            add("-nopreload")
            add("-limitvsconst")
            add("-softparticlesdefaultoff")
            add("-nohltv")
            add("-noaafonts")
            add("-nosound")
            add("-novid")
            add("+violence_hblood")
            add("0")
            add("+sethdmodels")
            add("0")
            add("+mat_disable_fancy_blending")
            add("1")
            add("+r_dynamic")
            add("0")
            add("+engine_no_focus_sleep")
            add("120")
            add("-nojoy")
        }

        val environmentVariables = mapOf(
            USER_PROFILE_ENVIRONMENT_KEY to userName,
            LOCAL_APPLICATION_DATA_ENVIRONMENT_KEY to LOCAL_APPLICATION_DATA_ENVIRONMENT_VALUE.format(userName),
            APPLICATION_DATA_ENVIRONMENT_KEY to APPLICATION_DATA_ENVIRONMENT_VALUE.format(userName),
            TEMP_ENVIRONMENT_KEY to TEMP_ENVIRONMENT_VALUE.format(userName),
            TMP_ENVIRONMENT_KEY to TMP_ENVIRONMENT_VALUE.format(userName)
        )

        return processHandler.createProcess(command = command, params = params, environmentVariables = environmentVariables)
    }

    suspend fun findSteamLoginAndPasswordAuthWindowDescriptor(steamProcessId: Long, userName: String): Boolean {
        return steamWindowHandler.findSteamLoginAndPasswordAuthWindowDescriptor(key = userName, steamProcessId = steamProcessId)
    }

    suspend fun findSteamSteamGuardCodeAuthWindowDescriptor(steamProcessId: Long, userName: String): Boolean {
        return steamWindowHandler.findSteamSteamGuardCodeAuthWindowDescriptor(key = userName, steamProcessId = steamProcessId)
    }

    suspend fun authWithLoginAndPassword(userName: String, password: String) {

        steamWindowHandler.setWindowOnTop(key = userName)

        steamWindowHandler.deleteAllFromWindow()
        steamWindowHandler.pasteTextInWindow(text = userName)
        steamWindowHandler.nextWindow()

        steamWindowHandler.setWindowOnTop(key = userName)

        steamWindowHandler.deleteAllFromWindow()
        steamWindowHandler.pasteTextInWindow(text = password)
        steamWindowHandler.nextWindow()

        steamWindowHandler.setWindowOnTop(key = userName)

//        if (SteamConfig.rememberMe.not()) {
//            steamWindowHandler.pressSpace()
//            SteamConfig.rememberMe = true
//        }

        steamWindowHandler.nextWindow()

        steamWindowHandler.setWindowOnTop(key = userName)

        steamWindowHandler.pressEnter()
    }

    suspend fun authWithSteamGuardCode(pid: Long, userName: String, steamGuardCode: String) {

        steamWindowHandler.findSteamLoginAndPasswordAuthWindowDescriptor(key = userName, steamProcessId = pid)

        steamWindowHandler.setWindowOnTop(key = userName)

        steamWindowHandler.pasteTextInWindow(text = steamGuardCode)
    }

    fun closeSteamWindow(steamProcessId: Long) {
        //steamWindowHandler.closeSteamWindows(steamProcessId)
    }

    companion object {
        private const val USER_PROFILE_ENVIRONMENT_KEY = "USERPROFILE"
        private const val LOCAL_APPLICATION_DATA_ENVIRONMENT_KEY = "LOCALAPPDATA"
        private const val APPLICATION_DATA_ENVIRONMENT_KEY = "APPDATA"
        private const val TEMP_ENVIRONMENT_KEY = "TEMP"
        private const val TMP_ENVIRONMENT_KEY = "TMP"
        private const val AC_SET_UP_SVC_PORT_ENVIRONMENT_KEY = "ACSETUPSVCPORT"

        private const val LOCAL_APPLICATION_DATA_ENVIRONMENT_VALUE = "C:\\Users\\nagib\\IdeaProjects\\CSPanelService\\remote\\src\\main\\resources\\steam\\%s\\AppData\\Local"
        private const val APPLICATION_DATA_ENVIRONMENT_VALUE = "C:\\Users\\nagib\\IdeaProjects\\CSPanelService\\remote\\src\\main\\resources\\steam\\%s\\AppData\\Roaming"
        private const val TEMP_ENVIRONMENT_VALUE = "C:\\Users\\nagib\\IdeaProjects\\CSPanelService\\remote\\src\\main\\resources\\steam\\%s\\Temp"
        private const val TMP_ENVIRONMENT_VALUE= "C:\\Users\\nagib\\IdeaProjects\\CSPanelService\\remote\\src\\main\\resources\\steam\\%s\\Temp"
    }
}