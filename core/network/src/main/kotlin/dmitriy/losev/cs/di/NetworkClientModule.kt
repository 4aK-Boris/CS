package dmitriy.losev.cs.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("dmitriy.losev.cs.clients")
internal class NetworkClientModule
