package com.vignesh.todo.common.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vignesh.todo.common.repository.model.TDTodoData

class TDTodoRepositoryTest : TDRepositoryImpl<TDTodoData>() {

    private val todoList by lazy {
        mutableListOf<TDTodoData>()
    }

    private val todoListLiveData by lazy {
        MutableLiveData<List<TDTodoData>>(todoList)
    }

    private fun refreshLiveData() {
        todoListLiveData.value = todoList
    }

    override suspend fun insert(data: TDTodoData) {
        todoList.add(data)
        refreshLiveData()
    }

    override suspend fun update(data: TDTodoData) {
        todoList.removeIf { it.id == data.id }
        todoList.add(data)
        refreshLiveData()
    }

    override suspend fun delete(data: TDTodoData) {
        todoList.removeIf { it.id == data.id }
        refreshLiveData()
    }

    override fun fetch(): LiveData<List<TDTodoData>> = todoListLiveData
}