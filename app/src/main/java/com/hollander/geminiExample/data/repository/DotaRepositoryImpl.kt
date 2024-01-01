package com.hollander.geminiExample.data.repository

import android.app.Application

import com.hollander.geminiExample.data.api.DotaApi
import com.hollander.geminiExample.data.dto.Hero
import com.hollander.geminiExample.domain.repository.DotaRepository
import com.hollander.geminiExample.R
import javax.inject.Inject


class DotaRepositoryImpl @Inject constructor(
    private val dotaApi: DotaApi,
    private val appContext: Application,
) : DotaRepository {

    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from the repository, The app name is $appName")
    }

    override suspend fun getHeroes(): List<Hero> {
        return dotaApi.getHeroes()
    }
}