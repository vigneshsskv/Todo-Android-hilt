package com.vignesh.todo.common.repository.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vignesh.todo.common.repository.local.db.dao.TDTodoCategoryDao
import com.vignesh.todo.common.repository.local.db.dao.LNTodoDao
import com.vignesh.todo.common.repository.local.db.dao.TDTodoStatusDao
import com.vignesh.todo.common.repository.model.TDTodoCategoryData
import com.vignesh.todo.common.repository.model.TDTodoData
import com.vignesh.todo.common.repository.model.TDTodoStatusData

@Database(
    entities = [TDTodoData::class, TDTodoCategoryData::class, TDTodoStatusData::class],
    version = 1,
    exportSchema = false
)
abstract class TDDatabase : RoomDatabase() {
    abstract fun getTodoDao(): LNTodoDao

    abstract fun getTodoCategoryDao(): TDTodoCategoryDao

    abstract fun getTodoStatusDao(): TDTodoStatusDao
}