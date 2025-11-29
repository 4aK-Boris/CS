package dmitriy.losev.cs.core.delegates

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dmitriy.losev.cs.core.StorageInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Factory
@PublishedApi
internal class DataStoreBooleanDelegate(
    private val storageInstance: StorageInstance,
    private val key: String? = null,
    private val defaultValue: Boolean = DEFAULT_VALUE
): ReadWriteProperty<Any?, Boolean> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean): Unit = runBlocking {
        val booleanKey = booleanPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.edit { preferences ->
            preferences[booleanKey] = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean = runBlocking {
        val booleanKey = booleanPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.data.map { preferences ->
            preferences[booleanKey] ?: defaultValue
        }.first()
    }

    companion object {
        private const val DEFAULT_VALUE = false
    }
}