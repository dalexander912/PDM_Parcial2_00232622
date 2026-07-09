package com.pdmcourse2026.basictemplate.screens.voting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse2026.basictemplate.RankeUcaApplication
import com.pdmcourse2026.basictemplate.data.models.Option
import com.pdmcourse2026.basictemplate.data.models.Question
import com.pdmcourse2026.basictemplate.data.repository.offlinefirst.VotingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VotingViewModel(
  private val repository: VotingRepository
) : ViewModel() {

  val questions: StateFlow<List<Question>> =
    repository.getQuestions()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
      )
  val options: StateFlow<List<Option>> =
    repository.getOptions()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
      )

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error: StateFlow<String?> = _error.asStateFlow()


  init { refresh() }

  fun refresh() {
    viewModelScope.launch {
      _error.value = null
      _isRefreshing.value = true
      try {
        repository.refresh()
      } catch (_: Exception) {
        // Solo mostramos error si además no hay nada en Room
        if (questions.value.isEmpty()) {
          _error.value = "Sin conexión y sin datos en caché"
        }
      }
      _isRefreshing.value = false
    }
  }

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val app =
          this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RankeUcaApplication
        VotingViewModel(app.appProvider.provideVotingRepository())
      }
    }
  }
}