package com.pdmcourse2026.basictemplate.data.repository

import com.pdmcourse2026.basictemplate.data.database.dao.OptionDao
import com.pdmcourse2026.basictemplate.data.database.entities.toEntity
import com.pdmcourse2026.basictemplate.data.database.entities.toModel
import com.pdmcourse2026.basictemplate.data.models.Option
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OptionRepositoryImpl(
  private val optionDao: OptionDao
) : OptionRepository {

  override fun getOptions(questionId: Int): Flow<List<Option>> {
    return optionDao.getOptionsForQuestion(questionId).map { entities ->
      entities.map { it.toModel() }
    }
  }

  override suspend fun addOption(value: String, imageUrl: String, questionId: Int) {
    val option = Option(value = value, imageUrl = imageUrl, questionId = questionId)
    optionDao.insertOption(option.toEntity())
  }

  override suspend fun updateOption(option: Option) {
    optionDao.updateOption(option.toEntity())
  }

  override suspend fun deleteOption(option: Option) {
    optionDao.deleteOption(option.toEntity())
  }
}