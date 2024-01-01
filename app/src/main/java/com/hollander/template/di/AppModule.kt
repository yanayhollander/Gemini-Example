package com.hollander.template.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.hollander.template.data.api.DotaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDotaApi(): DotaApi {
        return Retrofit.Builder()
            .baseUrl("https://api.opendota.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DotaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }
}