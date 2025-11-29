package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [DatabaseDomainUseCaseModule::class])
class DatabaseDomainModule