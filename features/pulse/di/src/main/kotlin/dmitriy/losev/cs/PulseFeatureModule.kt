package dmitriy.losev.cs

import dmitriy.losev.cs.di.PulseDataModule
import dmitriy.losev.cs.di.PulseDomainModule
import dmitriy.losev.cs.di.PulsePresentationModule
import org.koin.core.annotation.Module

@Module(includes = [PulseDomainModule::class, PulseDataModule::class, PulsePresentationModule::class])
class PulseFeatureModule
