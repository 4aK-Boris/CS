package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.Table

object CharmSaleOffersTable: Table(name = "market.charm_sale_offers") {

    val classId = long(name = "class_id")

    val instanceId = long(name = "instance_id")

    val listingId = long(name = "listing_id")

    val priceForSeller = integer(name = "price_for_seller")

    val priceForBuyer = integer(name = "price_for_buyer")

    val assetId = long(name = "asset_id")

    val link = varchar(name = "link", length = 512)

    override val primaryKey = PrimaryKey(listingId)

    init {
        foreignKey(classId, instanceId, target = CharmsInfoTable.primaryKey)
        index(false, classId, instanceId)
    }
}
