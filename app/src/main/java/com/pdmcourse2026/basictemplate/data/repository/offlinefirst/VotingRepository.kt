package com.pdmcourse2026.basictemplate.data.repository.offlinefirst

import com.pdmcourse2026.basictemplate.data.models.Option
import com.pdmcourse2026.basictemplate.data.models.Question
import com.pdmcourse2026.basictemplate.data.models.Vote
import kotlinx.coroutines.flow.Flow

interface VotingRepository {

  fun getQuestions(): Flow<List<Question>>
  fun getOptions(): Flow<List<Option>>

  suspend fun refresh()

  suspend fun postVotes(votes: List<Vote>)
}