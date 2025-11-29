package dmitriy.losev.cs.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CSDataRepositoryModule::class, CSDataMapperModule::class])
@ComponentScan("dmitriy.losev.cs.core")
class CSDataModule