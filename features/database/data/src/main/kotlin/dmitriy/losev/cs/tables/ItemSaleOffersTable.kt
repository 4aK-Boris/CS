package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.Table

object ItemSaleOffersTable: Table(name = "market.item_sale_offers") {

    val classId = ulong(name = "class_id")

    val instanceId = ulong(name = "instance_id")

    val listingId = ulong(name = "listing_id")

    val priceForSeller = integer(name = "price_for_seller")

    val priceForBuyer = integer(name = "price_for_buyer")

    val assetId = ulong(name = "asset_id")

    val link = varchar(name = "link", length = 256)

    override val primaryKey = PrimaryKey(listingId)

    init {
        foreignKey(classId, instanceId, target = ItemsInfoTable.primaryKey)
        index(false, classId, instanceId)
    }
}
