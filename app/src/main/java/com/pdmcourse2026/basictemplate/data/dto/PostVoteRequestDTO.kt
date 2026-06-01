package com.pdmcourse2026.basictemplate.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostVoteRequestDTO(
  val optionId: Int
)