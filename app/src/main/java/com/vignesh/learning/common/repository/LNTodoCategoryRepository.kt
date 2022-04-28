package com.vignesh.learning.common.repository

import com.vignesh.learning.common.repository.local.db.dao.LNTodoCategoryDao
import com.vignesh.learning.common.repository.model.LNTodoCategoryData
import javax.inject.Inject

class LNTodoCategoryRepository @Inject constructor(private val database: LNTodoCategoryDao) :
    LNRepositoryImpl<LNTodoCategoryData>() {
    override suspend fun insert(data: LNTodoCategoryData) = database.insertTodoCategory(data)

    override suspend fun update(data: LNTodoCategoryData) = database.updateTodoCategory(data)

    override suspend fun delete(data: LNTodoCategoryData) = database.updateTodoCategory(data)

    override fun fetch() = database.fetchTodoCategory()
}