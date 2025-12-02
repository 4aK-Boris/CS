package dmitiry.losev.cs.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("dmitiry.losev.cs.repositories")
internal class ProxyDataRepositoryModule
