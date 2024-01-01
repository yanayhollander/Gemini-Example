package com.hollander.template.domain.repository

import com.hollander.template.data.dto.Hero

interface DotaRepository {
    suspend fun getHeroes(): List<Hero>
}