package dmitriy.losev.cs.core

import dmitriy.losev.cs.dto.sales.SaleDTO
import java.io.File
import java.time.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun main() {

    val file = File("C:\\Users\\nagib\\IdeaProjects\\CSPanelService\\features\\market_csgo\\domain\\src\\main\\kotlin\\dmitriy\\losev\\cs\\core\\json.json")

    val jsonText = file.readText()

    val jsonObject = Json.parseToJsonElement(jsonText)

    val result = jsonObject.jsonObject["data"]?.jsonObject?.get("history")?.jsonArray?.map { array ->
        SaleDTO(price = array.jsonArray[2].jsonPrimitive.content.toDouble(), timestamp = Instant.ofEpochSecond(array.jsonArray[0].jsonPrimitive.content.toLong()))
    }!!.sortedBy { it.timestamp }

    result.forEach { saleDTO ->
        println(saleDTO)
    }

    val p = PriceEstimator()

    val pResult = p.estimate(sales = result, salesPerWeek = jsonObject.jsonObject["data"]?.jsonObject?.get("sales7d")?.jsonObject?.get("USD")?.jsonPrimitive?.content?.toInt()!!)

    println(pResult)
}