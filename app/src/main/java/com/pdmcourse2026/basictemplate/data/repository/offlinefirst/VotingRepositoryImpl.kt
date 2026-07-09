package com.pdmcourse2026.basictemplate.data.repository.offlinefirst

import com.pdmcourse2026.basictemplate.data.database.dao.OptionDao
import com.pdmcourse2026.basictemplate.data.database.dao.QuestionDao
import com.pdmcourse2026.basictemplate.data.database.entities.toModel
import com.pdmcourse2026.basictemplate.data.models.Option
import com.pdmcourse2026.basictemplate.data.models.Question
import com.pdmcourse2026.basictemplate.data.models.Vote
import com.pdmcourse2026.basictemplate.data.remote.KtorClient
import com.pdmcourse2026.basictemplate.data.remote.dto.OptionDto
import com.pdmcourse2026.basictemplate.data.remote.dto.QuestionDto
import com.pdmcourse2026.basictemplate.data.remote.dto.VoteRequestDto
import com.pdmcourse2026.basictemplate.data.remote.dto.toEntity
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class VotingRepositoryImpl(
  private val questionDao: QuestionDao,
  private val optionDao: OptionDao
) : VotingRepository {
  override fun getQuestions(): Flow<List<Question>> =
    questionDao.getQuestionsWithOptions().map { list -> list.map { it.toModel() } }
  override fun getOptions(): Flow<List<Option>> =
    optionDao.getOptions().map { entities ->
      entities.map { it.toModel() }
    }

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

  override suspend fun postVotes(votes: List<Vote>) {
//    val request = VoteRequestDto(votes = votes)
//
//    KtorClient.client.post("/parcialtres/votes") {
//      contentType(ContentType.Application.Json)
//      setBody(request)
//    }
  }
}