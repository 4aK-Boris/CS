package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object RareItemPatternsTable: IntIdTable(name = "market.rare_item_patterns") {

    val classId = ulong(name = "class_id")

    val instanceId = ulong(name = "instance_id")

    val startPattern = integer(name = "start_pattern")

    val endPattern = integer(name = "end_pattern")

    val minimalPrice = integer(name = "minimal_price")

    init {
        foreignKey(classId, instanceId, target = ItemsInfoTable.primaryKey)
        uniqueIndex(classId, instanceId)
    }
}
