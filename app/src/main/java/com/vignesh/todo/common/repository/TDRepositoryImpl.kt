package com.vignesh.todo.common.repository

import androidx.lifecycle.LiveData

abstract class TDRepositoryImpl<T> {
    abstract suspend fun insert(data: T)
    abstract suspend fun update(data: T)
    abstract suspend fun delete(data: T)
    abstract fun fetch(): LiveData<List<T>>
}