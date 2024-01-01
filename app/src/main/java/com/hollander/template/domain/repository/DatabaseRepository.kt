package com.hollander.template.domain.repository

import com.hollander.template.data.dto.Hero

interface DatabaseRepository {
    suspend fun saveHeroes(heroes: List<Hero>, force: Boolean = false)
}