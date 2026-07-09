package com.pdmcourse2026.basictemplate.data.remote.dto

import com.pdmcourse2026.basictemplate.data.models.Vote
import kotlinx.serialization.Serializable

@Serializable
data class VoteRequestDto(
  val votes: List<VoteDto>
)