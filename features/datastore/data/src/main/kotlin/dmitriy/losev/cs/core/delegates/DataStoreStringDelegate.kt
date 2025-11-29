package dmitriy.losev.cs.core.delegates

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dmitriy.losev.cs.core.StorageInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Factory
@PublishedApi
internal class DataStoreStringDelegate(
    private val storageInstance: StorageInstance,
    private val key: String? = null,
    private val defaultValue: String = DEFAULT_VALUE
): ReadWriteProperty<Any?, String> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String): Unit = runBlocking {
        val stringKey = stringPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.edit { preferences ->
            preferences[stringKey] = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String = runBlocking {
        val stringKey = stringPreferencesKey(name = key ?: property.name)
        storageInstance.dataStore.data.map { preferences ->
            preferences[stringKey] ?: defaultValue
        }.first()
    }

    companion object {
        private const val DEFAULT_VALUE = ""
    }
}