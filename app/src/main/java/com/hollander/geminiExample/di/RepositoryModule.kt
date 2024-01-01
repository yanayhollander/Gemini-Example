package com.hollander.geminiExample.di

import com.hollander.geminiExample.data.repository.DatabaseRepositoryImpl
import com.hollander.geminiExample.data.repository.DotaRepositoryImpl
import com.hollander.geminiExample.domain.repository.DatabaseRepository
import com.hollander.geminiExample.domain.repository.DotaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDotaRepository(
        dotaRepositoryImpl: DotaRepositoryImpl
    ): DotaRepository

    @Binds
    @Singleton
    abstract fun bindDatabaseRepository(
        databaseRepositoryImpl: DatabaseRepositoryImpl
    ): DatabaseRepository
}