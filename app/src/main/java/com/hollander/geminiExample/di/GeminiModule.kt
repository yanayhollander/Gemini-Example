package com.hollander.geminiExample.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.generationConfig
import com.hollander.geminiExample.BuildConfig
import com.hollander.geminiExample.data.api.DotaApi
import com.hollander.geminiExample.data.repository.DatabaseRepositoryImpl
import com.hollander.geminiExample.data.repository.DotaRepositoryImpl
import com.hollander.geminiExample.domain.repository.DatabaseRepository
import com.hollander.geminiExample.domain.repository.DotaRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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