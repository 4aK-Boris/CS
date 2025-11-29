package dmitriy.losev.cs

import dmitriy.losev.cs.di.FilesDataModule
import dmitriy.losev.cs.di.FilesDomainModule
import org.koin.core.annotation.Module

@Module(includes = [FilesDataModule::class, FilesDomainModule::class])
class FilesFeatureModule