package com.vignesh.learning.common.repository

import com.vignesh.learning.common.repository.local.db.dao.LNTodoStatusDao
import com.vignesh.learning.common.repository.model.LNTodoStatusData
import javax.inject.Inject

class LNTodoStatusRepository @Inject constructor(private val database: LNTodoStatusDao) :
    LNRepositoryImpl<LNTodoStatusData>() {
    override suspend fun insert(data: LNTodoStatusData) = database.insertTodoStatus(data)

    override suspend fun update(data: LNTodoStatusData) = database.updateTodoStatus(data)

    override suspend fun delete(data: LNTodoStatusData) = database.updateTodoStatus(data)

    override fun fetch() = database.fetchTodoCategory()
}