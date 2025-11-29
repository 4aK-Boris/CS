package dmitriy.losev.cs.di

import dmitriy.losev.cs.core.DataStoreInstance
import dmitriy.losev.cs.core.Storage
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module(includes = [DataStoreRepositoryModule::class, DataStoreMapperModule::class])
class DataStoreDataModule {

    @Singleton
    fun getStorage(dataStoreInstance: DataStoreInstance): Storage {
        return Storage(dataStoreInstance)
    }

    @Singleton
    fun getDataStoreInstance(): DataStoreInstance {
        return DataStoreInstance()
    }
}