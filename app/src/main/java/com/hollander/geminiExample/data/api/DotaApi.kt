package com.hollander.geminiExample.data.api

import com.hollander.geminiExample.data.dto.Hero
import retrofit2.http.GET

interface DotaApi {
    @GET("heroes")
    suspend fun getHeroes(): List<Hero>
}