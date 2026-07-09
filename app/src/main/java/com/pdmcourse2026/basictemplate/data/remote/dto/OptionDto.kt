package com.pdmcourse2026.basictemplate.data.remote.dto

import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity
import com.pdmcourse2026.basictemplate.data.models.Option
import kotlinx.serialization.Serializable

@Serializable
data class OptionDto (
  val id: Int = 0,
  val name: String,
  val imageUrl: String? = null,
  val votes: Int,
  val questionId: Int = 0
)

fun OptionDto.toModel(): Option {
  return Option(
    id = id,
    name = name,
    imageUrl = imageUrl,
    votes = votes,
    questionId = questionId
  )
}

fun OptionDto.toEntity(): OptionEntity {
  return OptionEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    votes = votes,
    questionId = questionId
  )
}