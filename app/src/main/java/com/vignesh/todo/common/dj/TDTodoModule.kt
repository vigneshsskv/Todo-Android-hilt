package com.vignesh.todo.common.dj

import com.vignesh.todo.common.repository.TDRepositoryImpl
import com.vignesh.todo.common.repository.TDTodoRepository
import com.vignesh.todo.common.repository.model.TDTodoData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TDTodoModule {
    @Binds
    @Singleton
    abstract fun getLNTodoRepository(repository: TDTodoRepository): TDRepositoryImpl<TDTodoData>
}