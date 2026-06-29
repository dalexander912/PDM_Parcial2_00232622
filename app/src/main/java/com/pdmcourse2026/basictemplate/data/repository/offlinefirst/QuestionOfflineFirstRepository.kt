package com.pdmcourse2026.basictemplate.data.repository.offlinefirst

import com.pdmcourse2026.basictemplate.data.database.entities.QuestionWithOptions
import com.pdmcourse2026.basictemplate.data.models.Option
import com.pdmcourse2026.basictemplate.data.models.Question
import kotlinx.coroutines.flow.Flow

interface QuestionOfflineFirstRepository {

  // Leer: de Room (fuente de verdad)
  fun getQuestions(): Flow<List<Question>>
  fun getOptions(questionId: Int): Flow<List<Option>>

  // Sincronizar: API -> Room
  suspend fun refresh()

  // Mutar: API -> luego refresh()
  suspend fun createQuestion(text: String)
  suspend fun updateQuestion(id: Int, text: String)
  suspend fun deleteQuestion(id: Int)

  suspend fun createOption(questionId: Int, value: String)
  suspend fun updateOption(id: Int, value: String, questionId: Int)
  suspend fun deleteOption(id: Int)
}