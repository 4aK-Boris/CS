package dmitriy.losev.cs.tables

import java.time.LocalDateTime
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.datetime

object ItemsInfoTable: Table(name = "market.items_info") {

    val classId = ulong(name = "class_id")
        .uniqueIndex()

    val instanceId = ulong(name = "instance_id")
        .uniqueIndex()

    val name = varchar(name = "name", length = 128)

    val lastCheckTime = datetime(name = "last_check_time")
        .default(defaultValue = LocalDateTime.now().minusMinutes(5L))

    override val primaryKey = PrimaryKey(classId, instanceId)
}
