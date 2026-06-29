package com.pdmcourse2026.basictemplate.screens.options

import androidx.lifecycle.ViewModel
import com.pdmcourse2026.basictemplate.RankeUcaApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse2026.basictemplate.data.models.Option
import com.pdmcourse2026.basictemplate.data.repository.offlinefirst.QuestionOfflineFirstRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OptionsViewModel(
  private val repository: QuestionOfflineFirstRepository,
  private val questionId: Int
) : ViewModel() {

  // ------------------ Leer --------------------- //

  val options: StateFlow<List<Option>> =
    repository.getOptions(questionId)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
      )

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error: StateFlow<String?> = _error.asStateFlow()

  // --------------- Sincronizar ----------------- //

  init { refresh() }

  fun refresh() {
    viewModelScope.launch {
      _error.value = null
      _isRefreshing.value = true
      try {
        repository.refresh()
      } catch (_: Exception) {
        // Solo mostramos error si además no hay nada en Room
        if (options.value.isEmpty()) {
          _error.value = "Sin conexión y sin datos en caché"
        }
      }
      _isRefreshing.value = false
    }
  }

  // ------------------ Mutar -------------------- //

  fun addOption(value: String, imageUrl: String) {
    viewModelScope.launch {
      try {
        repository.createOption(questionId, value)
        refresh()
      } catch (e: Exception) {
        // Si falla la API nos quedamos con lo que hay en Room
        e.printStackTrace()
      }
    }
  }

  fun updateOption(id: Int, value: String, imageUrl: String, questionId: Int) {
    viewModelScope.launch {
      try {
        repository.updateOption(id, value, questionId)
      } catch (e: Exception) {
        // Si falla la API nos quedamos con lo que hay en Room
        e.printStackTrace()
      }
    }
  }

  fun deleteOption(id: Int) {
    viewModelScope.launch {
      try {
        repository.deleteOption(id)
      } catch (e: Exception) {
        // Si falla la API nos quedamos con lo que hay en Room
        e.printStackTrace()
      }
    }
  }

  // ----------------- Factory ------------------- //

  companion object {
    fun provideFactory(questionId: Int) = viewModelFactory {
      initializer {
        val app = this[APPLICATION_KEY] as RankeUcaApplication
        OptionsViewModel(app.appProvider.provideQuestionOfflineFirstRepository(), questionId)
      }
    }
  }
}