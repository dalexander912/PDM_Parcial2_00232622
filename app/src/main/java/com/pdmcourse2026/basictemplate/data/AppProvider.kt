package com.pdmcourse2026.basictemplate.data

import android.content.Context
import com.pdmcourse2026.basictemplate.data.database.AppDatabase
import com.pdmcourse2026.basictemplate.data.repository.offlinefirst.QuestionOfflineFirstRepository
import com.pdmcourse2026.basictemplate.data.repository.offlinefirst.QuestionOfflineFirstRepositoryImpl

class AppProvider(context: Context) {

  private val appDatabase = AppDatabase.getDatabase(context)

  private val questionDao = appDatabase.questionDao()
  private val optionDao = appDatabase.optionDao()

  private val questionOfflineFirstRepository: QuestionOfflineFirstRepository =
    QuestionOfflineFirstRepositoryImpl(questionDao, optionDao)

  fun provideQuestionOfflineFirstRepository(): QuestionOfflineFirstRepository {
    return questionOfflineFirstRepository
  }
}