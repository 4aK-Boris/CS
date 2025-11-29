package dmitriy.losev.cs.core

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import dmitriy.losev.cs.SystemProperties
import okio.Path.Companion.toPath
import org.koin.core.annotation.Singleton

@Singleton
class DataStoreInstance: StorageInstance() {

    override val dataStore = PreferenceDataStoreFactory.createWithPath(
        produceFile = { "${SystemProperties.applicationDirectory}/$DATA_STORE_FILE_NAME".toPath() }
    )

    companion object {
        private const val DATA_STORE_FILE_NAME = "cs.preferences_pb"
    }
}