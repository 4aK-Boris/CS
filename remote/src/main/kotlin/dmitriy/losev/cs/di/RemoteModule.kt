package dmitriy.losev.cs.di

import dmitriy.losev.cs.AppModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.annotation.Module

@Module(includes = [AppModule::class])
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteModule