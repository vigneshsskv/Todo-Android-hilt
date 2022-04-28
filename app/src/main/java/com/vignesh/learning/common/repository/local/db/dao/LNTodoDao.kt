package com.vignesh.learning.common.repository.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vignesh.learning.common.repository.model.LNTodoData

sealed interface LN

@Dao
sealed interface LNTodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTODO(todo: LNTodoData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTODO(todo: LNTodoData)

    @Delete
    suspend fun deleteTODO(todo: LNTodoData)

    @Query("Select * from todo_list")
    fun fetchTodo(): LiveData<List<LNTodoData>>
}