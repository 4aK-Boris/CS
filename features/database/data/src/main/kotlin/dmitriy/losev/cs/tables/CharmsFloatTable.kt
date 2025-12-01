package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.Table

object CharmsFloatTable: Table(name = "market.charms_float") {

    val classId = long(name = "class_id")

    val instanceId = long(name = "instance_id")

    val itemId = long(name = "item_id")

    val listingId = long(name = "listing_id")
        .references(ref = CharmSaleOffersTable.listingId)

    val pattern = integer(name = "pattern")

    val a = long(name = "a")

    val d = long(name = "d")

    val m = long(name = "m")

    override val primaryKey = PrimaryKey(firstColumn = listingId)

    init {
        foreignKey(classId, instanceId, target = CharmsInfoTable.primaryKey)
        index(false, classId, instanceId)
        index(false, itemId)
    }
}
