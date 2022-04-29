package com.vignesh.todo.common.repository.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vignesh.todo.common.repository.model.TDTodoStatusData

@Dao
sealed interface TDTodoStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoStatus(todo: TDTodoStatusData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodoStatus(todo: TDTodoStatusData)

    @Delete
    suspend fun deleteTodoStatus(todo: TDTodoStatusData)

    @Query("Select * from todo_status")
    fun fetchTodoStatus(): LiveData<List<TDTodoStatusData>>
}