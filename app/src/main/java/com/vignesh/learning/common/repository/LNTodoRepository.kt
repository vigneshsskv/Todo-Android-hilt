package com.vignesh.learning.common.repository

import com.vignesh.learning.common.repository.local.db.dao.LNTodoDao
import com.vignesh.learning.common.repository.model.LNTodoData
import javax.inject.Inject

class LNTodoRepository @Inject constructor(private val database: LNTodoDao) :
    LNRepositoryImpl<LNTodoData>() {
    override suspend fun insert(data: LNTodoData) = database.insertTODO(data)

    override suspend fun update(data: LNTodoData) = database.updateTODO(data)

    override suspend fun delete(data: LNTodoData) = database.updateTODO(data)

    override fun fetch() = database.fetchTodo()
}