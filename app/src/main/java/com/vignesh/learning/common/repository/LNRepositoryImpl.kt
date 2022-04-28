package com.vignesh.learning.common.repository

import androidx.lifecycle.LiveData

sealed class LNRepositoryImpl<T> {
    abstract suspend fun insert(data: T)
    abstract suspend fun update(data: T)
    abstract suspend fun delete(data: T)
    abstract fun fetch(): LiveData<List<T>>
}