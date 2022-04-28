package com.vignesh.todo.dashboard.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.todo.common.repository.TDRepositoryImpl
import com.vignesh.todo.common.repository.model.TDTodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TDTodoListViewModel @Inject constructor(private val repository: TDRepositoryImpl<TDTodoData>) :
    ViewModel() {
    val todoList by lazy { repository.fetch() }

    fun editTodo(todo: TDTodoData) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }

    fun deleteTodoData(todo: TDTodoData) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }
}