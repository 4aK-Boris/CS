package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [DatabaseDataMapperModule::class, DatabaseDataRepositoryModule::class, DatabaseDataHandlerModule::class])
class DatabaseDataModule
