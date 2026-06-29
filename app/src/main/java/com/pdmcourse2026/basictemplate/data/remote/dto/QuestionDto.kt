package com.pdmcourse2026.basictemplate.data.remote.dto

import com.pdmcourse2026.basictemplate.data.database.entities.QuestionEntity
import com.pdmcourse2026.basictemplate.data.models.Question
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
  val id: Int = 0,
  val title: String,
  val optionCount: Int = 0
)

fun QuestionDto.toModel(): Question {
  return Question(
    id = id,
    title = title,
    optionCount = optionCount
  )
}

fun QuestionDto.toEntity(): QuestionEntity {
  return QuestionEntity(
    id = id,
    title = title,
    //optionCount = optionCount
  )
}