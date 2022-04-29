package com.vignesh.todo.common.repository.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vignesh.todo.common.repository.model.TDTodoData

@Dao
sealed interface TDTodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTODO(todo: TDTodoData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTODO(todo: TDTodoData)

    @Delete
    suspend fun deleteTODO(todo: TDTodoData)

    @Query("Select * from todo_list")
    fun fetchTodo(): LiveData<List<TDTodoData>>
}