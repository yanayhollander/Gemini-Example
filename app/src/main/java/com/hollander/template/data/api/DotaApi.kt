package com.hollander.template.data.api

import com.hollander.template.data.dto.Hero
import retrofit2.http.GET

interface DotaApi {
    @GET("heroes")
    suspend fun getHeroes(): List<Hero>
}