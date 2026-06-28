package com.pdmcourse2026.basictemplate.data.repository

import com.pdmcourse2026.basictemplate.data.models.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
  fun getQuestions(): Flow<List<Question>>
  suspend fun addQuestion(title: String)
  suspend fun updateQuestion(question: Question)
  suspend fun deleteQuestion(question: Question)
}