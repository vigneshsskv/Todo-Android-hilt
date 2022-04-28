package com.vignesh.learning.common.repository.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vignesh.learning.common.repository.local.db.dao.LNTodoCategoryDao
import com.vignesh.learning.common.repository.local.db.dao.LNTodoDao
import com.vignesh.learning.common.repository.local.db.dao.LNTodoStatusDao
import com.vignesh.learning.common.repository.model.LNTodoCategoryData
import com.vignesh.learning.common.repository.model.LNTodoData
import com.vignesh.learning.common.repository.model.LNTodoStatusData

@Database(
    entities = [LNTodoData::class, LNTodoCategoryData::class, LNTodoStatusData::class],
    version = 1,
    exportSchema = false
)
abstract class LNDatabase : RoomDatabase() {
    abstract fun getTodoDao(): LNTodoDao

    abstract fun getTodoCategoryDao(): LNTodoCategoryDao

    abstract fun getTodoStatusDao(): LNTodoStatusDao
}