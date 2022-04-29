package com.vignesh.todo.dashboard.edittodo

object TDEditTodoUtils {
    fun checkMade(oldValue: String?, newValue: String?) = oldValue != newValue

    fun emptyCheck(value: String?) = value == null || value.trim().isEmpty()
}