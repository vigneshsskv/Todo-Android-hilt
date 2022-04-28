package com.vignesh.todo.common.dj

import com.vignesh.todo.common.repository.local.db.TDDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TDAppModule {
    @Provides
    @Singleton
    fun getTodoDao(database: TDDatabase) = database.getTodoDao()

    @Provides
    @Singleton
    fun getTodoStatusDao(database: TDDatabase) = database.getTodoStatusDao()

    @Provides
    @Singleton
    fun getTodoCategoryDao(database: TDDatabase) = database.getTodoCategoryDao()
}


