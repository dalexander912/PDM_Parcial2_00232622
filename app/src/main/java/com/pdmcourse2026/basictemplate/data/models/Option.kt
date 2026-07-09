package com.pdmcourse2026.basictemplate.data.models

import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity

data class Option(
  val id: Int = 0,
  val name: String,
  val imageUrl: String? = null,
  val votes: Int,
  val questionId: Int = 0,
)

fun Option.toEntity(): OptionEntity {
  return OptionEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    votes = votes,
    questionId = questionId,
  )
}