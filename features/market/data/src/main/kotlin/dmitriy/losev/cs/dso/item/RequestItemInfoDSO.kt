package dmitriy.losev.cs.dso.item

data class RequestItemInfoDSO(
    private val itemName: String,
    private val start: String,
    private val count: String,
    private val searchDescriptions: Int = 0,
    private val sortColumn: String = POPULAR,
    private val sortDirection: String = DESC,
    private val appId: Int = CS_APP_ID,
    private val noRender: Int = 1
) {

    fun toParams(): Map<String, String> = mapOf(
        QUERY_PARAM_KEY to itemName,
        START_PARAM_KEY to start,
        COUNT_PARAM_KEY to count,
        SEARCH_DESCRIPTIONS_PARAM_KEY to searchDescriptions.toString(),
        SORT_COLUMN_PARAM_KEY to sortColumn,
        SORT_DIRECTORY_PARAM_KEY to sortDirection,
        APP_ID_PARAM_KEY to appId.toString(),
        NO_RENDER_PARAM_KEY to noRender.toString()
    )

    companion object {
        private const val POPULAR = "popular"
        private const val DESC = "desc"
        private const val CS_APP_ID = 730

        private const val QUERY_PARAM_KEY = "query"
        private const val START_PARAM_KEY = "start"
        private const val COUNT_PARAM_KEY = "count"
        private const val SEARCH_DESCRIPTIONS_PARAM_KEY = "search_descriptions"
        private const val SORT_COLUMN_PARAM_KEY = "sort_column"
        private const val SORT_DIRECTORY_PARAM_KEY = "sort_dir"
        private const val APP_ID_PARAM_KEY = "appid"
        private const val NO_RENDER_PARAM_KEY = "norender"
    }
}


