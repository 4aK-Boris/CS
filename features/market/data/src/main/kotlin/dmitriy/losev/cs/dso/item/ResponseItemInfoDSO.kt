package dmitriy.losev.cs.dso.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseItemInfoDSO(

    @SerialName(value = "success")
    val success: Boolean = false,

    @SerialName(value = "start")
    val start: Int = 0,

    @SerialName(value = "pagesize")
    val pagesize: Int = 0,

    @SerialName(value = "total_count")
    val totalCount: Int = 0,

    @SerialName(value = "searchdata")
    val searchData: SearchDataDSO = SearchDataDSO(),

    @SerialName(value = "results")
    val results: List<ResultDSO> = emptyList()
)


