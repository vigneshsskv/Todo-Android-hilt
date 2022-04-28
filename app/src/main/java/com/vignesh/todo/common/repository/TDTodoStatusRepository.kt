package com.vignesh.todo.common.repository

import com.vignesh.todo.common.repository.local.db.dao.TDTodoStatusDao
import com.vignesh.todo.common.repository.model.TDTodoStatusData
import javax.inject.Inject

class TDTodoStatusRepository @Inject constructor(private val database: TDTodoStatusDao) :
    TDRepositoryImpl<TDTodoStatusData>() {
    override suspend fun insert(data: TDTodoStatusData) = database.insertTodoStatus(data)

    override suspend fun update(data: TDTodoStatusData) = database.updateTodoStatus(data)

    override suspend fun delete(data: TDTodoStatusData) = database.updateTodoStatus(data)

    override fun fetch() = database.fetchTodoCategory()
}