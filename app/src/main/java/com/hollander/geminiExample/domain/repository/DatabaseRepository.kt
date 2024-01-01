package com.hollander.geminiExample.domain.repository

import com.hollander.geminiExample.data.dto.Hero

interface DatabaseRepository {
    suspend fun saveHeroes(heroes: List<Hero>, force: Boolean = false)
}