package com.vignesh.todo.common.repository

import com.vignesh.todo.common.repository.local.db.dao.TDTodoDao
import com.vignesh.todo.common.repository.model.TDTodoData
import javax.inject.Inject

class TDTodoRepository @Inject constructor(private val database: TDTodoDao) :
    TDRepositoryImpl<TDTodoData>() {
    override suspend fun insert(data: TDTodoData) = database.insertTODO(data)

    override suspend fun update(data: TDTodoData) = database.updateTODO(data)

    override suspend fun delete(data: TDTodoData) = database.updateTODO(data)

    override fun fetch() = database.fetchTodo()
}