package com.vignesh.learning.common.dj

import com.vignesh.learning.common.repository.LNRepositoryImpl
import com.vignesh.learning.common.repository.LNTodoCategoryRepository
import com.vignesh.learning.common.repository.model.LNTodoCategoryData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LNTodoStatusModule {
    @Binds
    @Singleton
    abstract fun getTodoCategory(repository: LNTodoCategoryRepository): LNRepositoryImpl<LNTodoCategoryData>
}