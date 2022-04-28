package com.vignesh.learning.common.dj

import com.vignesh.learning.common.repository.LNRepositoryImpl
import com.vignesh.learning.common.repository.LNTodoRepository
import com.vignesh.learning.common.repository.model.LNTodoData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LNTodoModule {
    @Binds
    @Singleton
    abstract fun getLNTodoRepository(repository: LNTodoRepository): LNRepositoryImpl<LNTodoData>
}