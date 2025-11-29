package dmitriy.losev.cs.dso.item

import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDataDSO(

    @SerialName(value = "query")
    val query: String = EMPTY_STRING,

    @SerialName(value = "search_descriptions")
    val searchDescriptions: Boolean = false,

    @SerialName(value = "total_count")
    val totalCount: Int = 0,

    @SerialName(value = "pagesize")
    val pageSize: Int = 0,

    @SerialName(value = "prefix")
    val prefix: String = EMPTY_STRING,

    @SerialName(value = "class_prefix")
    val classPrefix: String = EMPTY_STRING
)