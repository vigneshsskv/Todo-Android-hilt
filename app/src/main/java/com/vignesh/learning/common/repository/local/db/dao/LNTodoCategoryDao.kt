package com.vignesh.learning.common.repository.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vignesh.learning.common.repository.model.LNTodoCategoryData

@Dao
sealed interface LNTodoCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoCategory(todo: LNTodoCategoryData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodoCategory(todo: LNTodoCategoryData)

    @Delete
    suspend fun deleteTodoCategory(todo: LNTodoCategoryData)

    @Query("Select * from todo_category")
    fun fetchTodoCategory(): LiveData<List<LNTodoCategoryData>>
}