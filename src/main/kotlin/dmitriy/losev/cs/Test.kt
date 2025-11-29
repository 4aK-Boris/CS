package dmitriy.losev.cs.dmitriy.losev.cs

import java.io.File
import kotlinx.io.files.Path
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

fun replaceGamesPlayedWhileIdle(
    input: String,
    ids: List<Int>
) {
    val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
    val root = json.parseToJsonElement(File(input).readText()).jsonObject
    val newArray = buildJsonArray { ids.forEach { add(JsonPrimitive(it)) } }
    val updated = JsonObject(root.toMutableMap().apply {
        put("GamesPlayedWhileIdle", newArray)
    })
    File(input).writeText(json.encodeToString(JsonObject.serializer(), updated))
}

// пример вызова
fun main() {
    val ids = listOf(
        570,440,3127280,578080,236390,2923300,1172470,2357570,230410,1085660,238960,386180,
        1172470,230410,440,1085660,238960,3766920,3697340,3626910,3840970,3868440
        ,386180,1568590,291550,304930,386360,2437170,444090,700330,730,761890,2073850,
        2074920,760160,1549250,1974050,918570,570,578080,236390,2923300
    ).distinct().sorted()
    File("E:\\ASF-win-x64\\config").listFiles().filter<File> { it.name.endsWith(".json") }.forEach {
        replaceGamesPlayedWhileIdle(input = it.absolutePath, ids = ids)
    }
}