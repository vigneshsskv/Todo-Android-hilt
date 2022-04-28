package com.vignesh.todo.common.repository

import com.vignesh.todo.common.repository.local.db.dao.TDTodoCategoryDao
import com.vignesh.todo.common.repository.model.TDTodoCategoryData
import javax.inject.Inject

class TDTodoCategoryRepository @Inject constructor(private val database: TDTodoCategoryDao) :
    TDRepositoryImpl<TDTodoCategoryData>() {
    override suspend fun insert(data: TDTodoCategoryData) = database.insertTodoCategory(data)

    override suspend fun update(data: TDTodoCategoryData) = database.updateTodoCategory(data)

    override suspend fun delete(data: TDTodoCategoryData) = database.updateTodoCategory(data)

    override fun fetch() = database.fetchTodoCategory()
}