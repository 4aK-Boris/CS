package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(
    includes = [
        SteamPresentationTaskModule::class,
        SteamPresentationServiceModule::class,
        SteamPresentationMapperModule::class,
        SteamPresentationDescriptionModule::class,
        SteamPresentationValidationModule::class
    ]
)
class SteamPresentationModule
