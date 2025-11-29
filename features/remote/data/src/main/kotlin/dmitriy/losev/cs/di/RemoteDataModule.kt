package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [RemoteDataMapperModule::class, RemoteDataRepositoryModule::class])
class RemoteDataModule