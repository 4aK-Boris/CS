package dmitriy.losev.cs.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dmitriy.losev.cs.core.delegates.DataStoreBooleanDelegate
import dmitriy.losev.cs.core.delegates.DataStoreIntDelegate
import dmitriy.losev.cs.core.delegates.DataStoreLongDelegate
import dmitriy.losev.cs.core.delegates.DataStoreStringDelegate
import kotlin.properties.ReadWriteProperty

abstract class StorageInstance {

    abstract val dataStore: DataStore<Preferences>

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> preference(key: String, defaultValue: T): ReadWriteProperty<Any?, T> = when(T::class) {
        Boolean::class -> DataStoreBooleanDelegate(storageInstance = this, key = key, defaultValue = defaultValue as Boolean)
        String::class -> DataStoreStringDelegate(storageInstance = this, key = key, defaultValue = defaultValue as String)
        Int::class -> DataStoreIntDelegate(storageInstance = this, key = key, defaultValue = defaultValue as Int)
        Long::class -> DataStoreLongDelegate(storageInstance = this, key = key, defaultValue = defaultValue as Long)
        else -> throw IllegalStateException("Unsupported preference type ${T::class}")
    } as ReadWriteProperty<Any?, T>
}