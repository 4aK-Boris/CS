package dmitriy.losev.cs.core

import java.util.*
import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.Table

fun Table.androidDeviceId(name: String) = registerColumn(
    name = name,
    type = object : ColumnType<String>() {

        override fun sqlType() = "UUID"

        override fun valueFromDB(value: Any): String {
            return "android:$value"
        }

        override fun notNullValueToDB(value: String): Any {
            return UUID.fromString(value.removePrefix("android:"))
        }
    }
)
