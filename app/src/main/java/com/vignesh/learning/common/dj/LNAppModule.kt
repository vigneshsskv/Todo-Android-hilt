package com.vignesh.learning.common.dj

import com.vignesh.learning.common.repository.local.db.LNDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LNAppModule {
    @Provides
    @Singleton
    fun getTodoDao(database: LNDatabase) = database.getTodoDao()

    @Provides
    @Singleton
    fun getTodoStatusDao(database: LNDatabase) = database.getTodoStatusDao()

    @Provides
    @Singleton
    fun getTodoCategoryDao(database: LNDatabase) = database.getTodoCategoryDao()
}


