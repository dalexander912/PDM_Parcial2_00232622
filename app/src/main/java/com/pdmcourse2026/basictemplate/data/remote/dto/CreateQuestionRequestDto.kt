package com.pdmcourse2026.basictemplate.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateQuestionRequestDto(
  val title: String
)