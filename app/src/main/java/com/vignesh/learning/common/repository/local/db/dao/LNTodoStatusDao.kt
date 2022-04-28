package com.vignesh.learning.common.repository.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vignesh.learning.common.repository.model.LNTodoStatusData

@Dao
sealed interface LNTodoStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoStatus(todo: LNTodoStatusData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodoStatus(todo: LNTodoStatusData)

    @Delete
    suspend fun deleteTodoStatus(todo: LNTodoStatusData)

    @Query("Select * from todo_category")
    fun fetchTodoCategory(): LiveData<List<LNTodoStatusData>>
}