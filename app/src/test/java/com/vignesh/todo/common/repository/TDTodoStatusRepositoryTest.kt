package com.vignesh.todo.common.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vignesh.todo.common.repository.model.TDTodoStatusData

class TDTodoStatusRepositoryTest : TDRepositoryImpl<TDTodoStatusData>() {

    private val todoStatusList by lazy {
        mutableListOf<TDTodoStatusData>()
    }

    private val todoStatusListLiveData by lazy {
        MutableLiveData<List<TDTodoStatusData>>(todoStatusList)
    }

    private fun refreshLiveData() {
        todoStatusListLiveData.value = todoStatusList
    }

    override suspend fun insert(data: TDTodoStatusData) {
        todoStatusList.add(data)
        refreshLiveData()
    }

    override suspend fun update(data: TDTodoStatusData) {
        todoStatusList.removeIf { it.id == data.id }
        todoStatusList.add(data)
        refreshLiveData()
    }

    override suspend fun delete(data: TDTodoStatusData) {
        todoStatusList.removeIf { it.id == data.id }
        refreshLiveData()
    }

    override fun fetch(): LiveData<List<TDTodoStatusData>> = todoStatusListLiveData
}