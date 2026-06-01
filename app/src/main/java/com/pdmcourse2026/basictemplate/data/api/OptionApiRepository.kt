package com.pdmcourse2026.basictemplate.data.api

import com.pdmcourse2026.basictemplate.data.dto.OptionDTO
import com.pdmcourse2026.basictemplate.data.dto.PostResponseDTO
import com.pdmcourse2026.basictemplate.data.dto.PostVoteRequestDTO
import com.pdmcourse2026.basictemplate.data.dto.toModel
import com.pdmcourse2026.basictemplate.models.Option
import com.pdmcourse2026.basictemplate.models.PostResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class OptionApiRepository: OptionRepository {
  override suspend fun getOptions(): Result<List<Option>> {
    try {
      val response: List<OptionDTO> = KtorClient.client.get("options") {
      }.body()
      return Result.success(response.map { optionDTO -> optionDTO.toModel() })

    } catch (e: Exception) {
      return Result.failure(e)
    }
  }

  override suspend fun postVote(optionId: Int): Result<PostResponse> {
    try {
      val request = PostVoteRequestDTO(
        optionId = optionId
      )

      val response: PostResponseDTO = KtorClient.client.post("vote") {
        contentType(ContentType.Application.Json)
        setBody(request)
      }.body()
      return Result.success(response.toModel())

    } catch (e: Exception) {
      return Result.failure(e)
    }
  }
}