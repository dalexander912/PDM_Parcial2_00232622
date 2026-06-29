package com.pdmcourse2026.basictemplate.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateOptionRequestDto(
  val name: String,
  val questionId: Int,
  val imageUrl: String?
)