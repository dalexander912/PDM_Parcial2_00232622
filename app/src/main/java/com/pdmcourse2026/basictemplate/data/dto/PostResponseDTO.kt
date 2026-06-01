package com.pdmcourse2026.basictemplate.data.dto

import com.pdmcourse2026.basictemplate.models.PostResponse
import kotlinx.serialization.Serializable

@Serializable
data class PostResponseDTO(
  val ok: Boolean,
  val response: String
)

fun PostResponseDTO.toModel(): PostResponse {
  return PostResponse(
    ok = ok,
    response = response
  )
}