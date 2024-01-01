package com.hollander.template.presentation

import com.hollander.template.data.dto.Hero
import com.hollander.template.domain.repository.DatabaseRepository
import com.hollander.template.domain.repository.DotaRepository
import com.hollander.template.presentation.viewModel.AsyncViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DotaViewModel @Inject constructor(
    private val dotaRepository: DotaRepository,
    private val databaseRepository: DatabaseRepository
) : AsyncViewModel() {

    private val _action = MutableStateFlow<Action?>(null)
    val action get() = _action

    init {
        launchWithState {
            val heroes = dotaRepository.getHeroes()
            _action.value = Action.ShowHeroes(heroes)
            databaseRepository.saveHeroes(heroes, force = true)
            Timber.d("${heroes.size} heroes loaded successfully")
        }
    }

    sealed interface Action {
        data class ShowHeroes(val heroes: List<Hero>) : Action
    }
}