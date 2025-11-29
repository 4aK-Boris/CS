package dmitriy.losev.cs.tables

import java.time.LocalDateTime
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.datetime

object CharmsInfoTable: Table(name = "market.charms_info") {

    val classId = long(name = "class_id")
        .uniqueIndex()

    val instanceId = long(name = "instance_id")
        .uniqueIndex()

    val name = varchar(name = "name", length = 128)

    val lastCheckTime = datetime(name = "last_check_time")
        .default(defaultValue = LocalDateTime.now().minusMinutes(5L))

    override val primaryKey = PrimaryKey(classId, instanceId)
}