package com.vignesh.todo.common.dj

import com.vignesh.todo.common.repository.TDRepositoryImpl
import com.vignesh.todo.common.repository.TDTodoCategoryRepository
import com.vignesh.todo.common.repository.model.TDTodoCategoryData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TDTodoStatusModule {
    @Binds
    @Singleton
    abstract fun getTodoCategory(repository: TDTodoCategoryRepository): TDRepositoryImpl<TDTodoCategoryData>
}