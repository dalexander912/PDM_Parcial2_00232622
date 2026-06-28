package com.pdmcourse2026.basictemplate.screens.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse2026.basictemplate.RankeUcaApplication
import com.pdmcourse2026.basictemplate.data.models.Question
import com.pdmcourse2026.basictemplate.data.repository.QuestionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuestionsViewModel (
  private val questionRepository: QuestionRepository
) : ViewModel() {
  val questions: StateFlow<List<Question>> =
    questionRepository.getQuestions()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5_000),
        initialValue = emptyList()
      )

  fun addQuestion(title: String) {
    viewModelScope.launch {
      questionRepository.addQuestion(title)
    }
  }

  fun updateQuestion(question: Question, title: String) {
    viewModelScope.launch {
      val updatedQuestion = question.copy(title = title)
      questionRepository.updateQuestion(updatedQuestion)
    }
  }

  fun deleteQuestion(question: Question) {
    viewModelScope.launch {
      questionRepository.deleteQuestion(question)
    }
  }

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val app =
          this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as RankeUcaApplication
        QuestionsViewModel(app.appProvider.provideQuestionRepository())
      }
    }
  }
}