package dmitriy.losev.cs.core

enum class SortSkinsOrder(val title: String) {
    OLDEST(title = "oldest"),
    NEWEST(title = "newest"),
    LOWEST_PRICE(title = "lowest_price"),
    HIGHEST_PRICE(title = "highest_price");
}