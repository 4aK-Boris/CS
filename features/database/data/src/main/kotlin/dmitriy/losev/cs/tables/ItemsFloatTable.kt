package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.Table

object ItemsFloatTable: Table(name = "market.items_float") {

    val classId = ulong(name = "class_id")

    val instanceId = ulong(name = "instance_id")

    val itemId = ulong(name = "item_id")

    val listingId = ulong(name = "listing_id").references(ref = ItemSaleOffersTable.listingId)

    val float = double(name = "float")

    val pattern = integer(name = "pattern")

    val s = ulong(name = "s")

    val a = ulong(name = "a")

    val d = ulong(name = "d")

    val m = ulong(name = "m")

    override val primaryKey = PrimaryKey(firstColumn = listingId)

    init {
        foreignKey(classId, instanceId, target = ItemsInfoTable.primaryKey)
        index(false, classId, instanceId)
        index(false, itemId)
    }
}