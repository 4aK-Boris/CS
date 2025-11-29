package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(includes = [CSDomainUseCaseModule::class])
class CSDomainModule