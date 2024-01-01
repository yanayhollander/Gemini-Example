package com.hollander.geminiExample.domain.repository

import com.hollander.geminiExample.data.dto.Hero

interface DotaRepository {
    suspend fun getHeroes(): List<Hero>
}