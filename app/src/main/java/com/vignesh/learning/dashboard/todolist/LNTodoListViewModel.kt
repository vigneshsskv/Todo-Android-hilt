package com.vignesh.learning.dashboard.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.learning.common.repository.LNRepositoryImpl
import com.vignesh.learning.common.repository.model.LNTodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LNTodoListViewModel @Inject constructor(private val repository: LNRepositoryImpl<LNTodoData>) :
    ViewModel() {
    val todoList by lazy { repository.fetch() }

    fun editTodo(todo: LNTodoData) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }

    fun deleteTodoData(todo: LNTodoData) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }
}