package com.hollander.geminiExample.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.hollander.geminiExample.data.dto.Hero
import com.hollander.geminiExample.domain.repository.DatabaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): DatabaseRepository {
    private val heroesRef = db.collection("heroes")
    override suspend fun saveHeroes(heroes: List<Hero>, force: Boolean) {
        if (force) {
            saveHeroes(heroes)
        } else {
            // update collection if empty
            if (heroesRef.limit(1).get().await().isEmpty) {
                saveHeroes(heroes)
            }
        }
    }

    private suspend fun saveHeroes(heroes: List<Hero>) {
        val batch = db.batch()
        heroes.forEach { hero ->
            batch.set(heroesRef.document(hero.localized_name), hero)
        }
        batch.commit().await()
    }
}