package com.vignesh.todo.common.repository

import androidx.lifecycle.MutableLiveData
import com.vignesh.todo.common.repository.model.TDTodoCategoryData

class TDTodoCategoryRepositoryTest : TDRepositoryImpl<TDTodoCategoryData>() {

    private val todoCategoryList by lazy {
        mutableListOf<TDTodoCategoryData>()
    }

    private val todoListLiveData by lazy {
        MutableLiveData<List<TDTodoCategoryData>>(todoCategoryList)
    }

    private fun refreshLiveData() {
        todoListLiveData.value = todoCategoryList
    }

    override suspend fun insert(data: TDTodoCategoryData) {
        todoCategoryList.add(data)
        refreshLiveData()
    }

    override suspend fun update(data: TDTodoCategoryData) {
        todoCategoryList.removeIf { it.id == data.id }
        todoCategoryList.add(data)
        refreshLiveData()
    }

    override suspend fun delete(data: TDTodoCategoryData) {
        todoCategoryList.removeIf { it.id == data.id }
        refreshLiveData()
    }

    override fun fetch() = todoListLiveData
}