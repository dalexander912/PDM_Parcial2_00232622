package com.pdmcourse2026.basictemplate.data.dto

import com.pdmcourse2026.basictemplate.models.Option
import kotlinx.serialization.Serializable

@Serializable
data class OptionDTO(
  val id: Int,
  val imageUrl: String,
  val name: String,
  val votes: Int
)

fun OptionDTO.toModel(): Option {
  return Option(
    id = id,
    imageUrl = imageUrl,
    name = name,
    votes = votes
  )
}