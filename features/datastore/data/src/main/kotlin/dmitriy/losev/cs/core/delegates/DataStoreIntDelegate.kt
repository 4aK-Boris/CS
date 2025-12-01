package dmitriy.losev.cs.core.delegates

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dmitriy.losev.cs.core.StorageInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Factory
@PublishedApi
internal class DataStoreIntDelegate(
    private val storageInstance: StorageInstance,
    private val key: String? = null,
    private val defaultValue: Int = DEFAULT_VALUE
): ReadWriteProperty<Any?, Int> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int): Unit = runBlocking {
        val intKey = intPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.edit { preferences ->
            preferences[intKey] = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int = runBlocking {
        val intKey = intPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.data.map { preferences ->
            preferences[intKey] ?: defaultValue
        }.first()
    }

    companion object {
        private const val DEFAULT_VALUE = 0
    }
}