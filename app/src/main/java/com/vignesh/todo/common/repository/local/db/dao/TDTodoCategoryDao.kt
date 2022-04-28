package com.vignesh.todo.common.repository.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vignesh.todo.common.repository.model.TDTodoCategoryData

@Dao
sealed interface TDTodoCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoCategory(todo: TDTodoCategoryData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodoCategory(todo: TDTodoCategoryData)

    @Delete
    suspend fun deleteTodoCategory(todo: TDTodoCategoryData)

    @Query("Select * from todo_category")
    fun fetchTodoCategory(): LiveData<List<TDTodoCategoryData>>
}