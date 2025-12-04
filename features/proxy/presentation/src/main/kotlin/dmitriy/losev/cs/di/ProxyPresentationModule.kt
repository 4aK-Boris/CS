package dmitriy.losev.cs.di

import org.koin.core.annotation.Module

@Module(
    includes = [
        ProxyPresentationServiceModule::class,
        ProxyPresentationMapperModule::class,
        ProxyPresentationDescriptionModule::class,
        ProxyPresentationValidationModule::class,
        ProxyPresentationExtractorModule::class
    ]
)
class ProxyPresentationModule
