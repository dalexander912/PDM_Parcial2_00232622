package com.pdmcourse2026.basictemplate.data.repository.offlinefirst

import com.pdmcourse2026.basictemplate.data.database.dao.OptionDao
import com.pdmcourse2026.basictemplate.data.database.dao.QuestionDao
import com.pdmcourse2026.basictemplate.data.database.entities.QuestionWithOptions
import com.pdmcourse2026.basictemplate.data.models.Option
import com.pdmcourse2026.basictemplate.data.models.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.pdmcourse2026.basictemplate.data.database.entities.toModel
import com.pdmcourse2026.basictemplate.data.remote.KtorClient
import com.pdmcourse2026.basictemplate.data.remote.dto.CreateOptionRequestDto
import com.pdmcourse2026.basictemplate.data.remote.dto.CreateQuestionRequestDto
import com.pdmcourse2026.basictemplate.data.remote.dto.OptionDto
import com.pdmcourse2026.basictemplate.data.remote.dto.QuestionDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.pdmcourse2026.basictemplate.data.remote.dto.toEntity
import io.ktor.client.request.delete
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class QuestionOfflineFirstRepositoryImpl(
  private val questionDao: QuestionDao,
  private val optionDao: OptionDao
) : QuestionOfflineFirstRepository {

  // Leer: de Room (fuente de verdad)

  override fun getQuestions(): Flow<List<Question>> =
    questionDao.getQuestionsWithOptions().map { list -> list.map { it.toModel() } }

  override fun getOptions(questionId: Int): Flow<List<Option>> =
    optionDao.getOptionsForQuestion(questionId).map { entities ->
      entities.map { it.toModel() }
    }

  // Sincronizar: API -> Room

  override suspend fun refresh() {
    val questions: List<QuestionDto> =
      KtorClient.client.get("questions") {
      }.body()
    val options: List<OptionDto> =
      KtorClient.client.get("options") {
      }.body()
    questionDao.upsertAll(questions.map { it.toEntity() })
    optionDao.upsertAll(options.map { it.toEntity() })
  }

  // Mutar: API -> luego refresh()

  // Questions //

  override suspend fun createQuestion(text: String) {
    val request = CreateQuestionRequestDto(title = text)

    KtorClient.client.post("questions") {
      contentType(ContentType.Application.Json)
      setBody(request)
    }
  }

  override suspend fun updateQuestion(id: Int, text: String) {
    val request = CreateQuestionRequestDto(title = text)

    KtorClient.client.put("questions/$id") {
      contentType(ContentType.Application.Json)
      setBody(request)
    }
  }

  override suspend fun deleteQuestion(id: Int) {
    KtorClient.client.delete("questions/$id")
  }

  // Options //

  override suspend fun createOption(questionId: Int, value: String) {
    val request = CreateOptionRequestDto(
      name = value,
      questionId = questionId,
      imageUrl = null
    )

    KtorClient.client.post("options") {
      contentType(ContentType.Application.Json)
      setBody(request)
    }
  }

  override suspend fun updateOption(id: Int, value: String, questionId: Int) {
    val request = CreateOptionRequestDto(
      name = value,
      questionId = questionId,
      imageUrl = null
    )

    KtorClient.client.put("options/$id") {
      contentType(ContentType.Application.Json)
      setBody(request)
    }
  }

  override suspend fun deleteOption(id: Int) {
    KtorClient.client.delete("options/$id")
  }
}