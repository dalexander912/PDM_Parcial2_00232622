package com.pdmcourse2026.basictemplate.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity
import com.pdmcourse2026.basictemplate.data.database.entities.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptionDao {

  @Transaction
  @Query("SELECT * FROM options WHERE questionId = :questionId")
  fun getOptionsForQuestion(questionId: Int): Flow<List<OptionEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOption(option: OptionEntity)

  @Update
  suspend fun updateOption(option: OptionEntity)

  @Delete
  suspend fun deleteOption(option: OptionEntity)

  @Upsert
  suspend fun upsertAll(options: List<OptionEntity>)
}