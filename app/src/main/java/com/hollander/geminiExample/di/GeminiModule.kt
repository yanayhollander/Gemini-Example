package com.hollander.geminiExample.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.generationConfig
import com.hollander.geminiExample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GeminiPro

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GeminiVision

@Module
@InstallIn(SingletonComponent::class)
object GeminiModule {

    @Provides
    @Singleton
    @GeminiPro
    fun provideGenerativeModelPro(config: GenerationConfig): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.apiKey,
            generationConfig = config
        )
    }

    @Provides
    @Singleton
    @GeminiVision
    fun provideGenerativeModelVision(config: GenerationConfig): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = BuildConfig.apiKey,
            generationConfig = config
        )
    }

    @Provides
    @Singleton
    fun provideGenerationConfig() = generationConfig {
        temperature = 0.7f
    }
}