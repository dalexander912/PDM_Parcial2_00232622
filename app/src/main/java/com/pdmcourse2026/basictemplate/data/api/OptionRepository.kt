package com.pdmcourse2026.basictemplate.data.api

import com.pdmcourse2026.basictemplate.models.Option
import com.pdmcourse2026.basictemplate.models.PostResponse

interface OptionRepository {
  suspend fun getOptions(): Result<List<Option>>
  suspend fun postVote(optionId: Int): Result<PostResponse>
}