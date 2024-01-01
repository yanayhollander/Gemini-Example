package com.hollander.template.di

import com.hollander.template.data.repository.DatabaseRepositoryImpl
import com.hollander.template.data.repository.DotaRepositoryImpl
import com.hollander.template.domain.repository.DatabaseRepository
import com.hollander.template.domain.repository.DotaRepository
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