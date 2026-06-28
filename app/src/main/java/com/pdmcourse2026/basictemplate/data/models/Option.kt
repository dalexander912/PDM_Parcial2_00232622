package com.pdmcourse2026.basictemplate.data.models

import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity

data class Option(
  val id: Int = 0,
  val value: String,
  val imageUrl: String? = null,
  val questionId: Int = 0,
)

fun Option.toEntity(): OptionEntity {
  return OptionEntity(
    id = id,
    value = value,
    imageUrl = imageUrl,
    questionId = questionId,
  )
}