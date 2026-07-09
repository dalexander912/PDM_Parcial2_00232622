package com.pdmcourse2026.basictemplate.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VoteDto(
  val questionId: Int,
  val optionId: Int
)