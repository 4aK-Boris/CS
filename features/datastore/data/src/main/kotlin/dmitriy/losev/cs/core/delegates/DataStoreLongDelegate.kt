package dmitriy.losev.cs.core.delegates

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import dmitriy.losev.cs.core.StorageInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Factory
@PublishedApi
internal class DataStoreLongDelegate(
    private val storageInstance: StorageInstance,
    private val key: String? = null,
    private val defaultValue: Long = DEFAULT_VALUE
): ReadWriteProperty<Any?, Long> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long): Unit = runBlocking {
        val longKey = longPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.edit { preferences ->
            preferences[longKey] = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Long = runBlocking {
        val longKey = longPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.data.map { preferences ->
            preferences[longKey] ?: defaultValue
        }.first()
    }

    companion object {
        private const val DEFAULT_VALUE = 0L
    }
}