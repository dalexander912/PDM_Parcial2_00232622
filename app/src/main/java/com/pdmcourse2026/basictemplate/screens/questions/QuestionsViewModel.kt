package com.pdmcourse2026.basictemplate.screens.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse2026.basictemplate.RankeUcaApplication
import com.pdmcourse2026.basictemplate.data.database.entities.QuestionWithOptions
import com.pdmcourse2026.basictemplate.data.models.Question
import com.pdmcourse2026.basictemplate.data.repository.offlinefirst.QuestionOfflineFirstRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuestionsViewModel (
  private val repository: QuestionOfflineFirstRepository
) : ViewModel() {

  // ------------------ Leer --------------------- //

  val questions: StateFlow<List<Question>> =
    repository.getQuestions()
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
        if (questions.value.isEmpty()) {
          _error.value = "Sin conexión y sin datos en caché"
        }
      }
      _isRefreshing.value = false
    }
  }

  // ------------------ Mutar -------------------- //

  fun addQuestion(text: String) {
    viewModelScope.launch {
      try {
        repository.createQuestion(text)
        refresh()
      } catch (e: Exception) {
        // Si falla la API nos quedamos con lo que hay en Room
        e.printStackTrace()
      }
    }
  }

  fun updateQuestion(id: Int, text: String) {
    viewModelScope.launch {
      try {
        repository.updateQuestion(id, text)
      } catch (e: Exception) {
        // Si falla la API nos quedamos con lo que hay en Room
        e.printStackTrace()
      }
    }
  }

  fun deleteQuestion(id: Int) {
    viewModelScope.launch {
      try {
        repository.deleteQuestion(id)
        refresh()
      } catch (e: Exception) {
        // Si falla la API nos quedamos con lo que hay en Room
        e.printStackTrace()
      }
    }
  }

  // ----------------- Factory ------------------- //

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val app =
          this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RankeUcaApplication
        QuestionsViewModel(app.appProvider.provideQuestionOfflineFirstRepository())
      }
    }
  }
}