package com.vignesh.todo.common.dj

import android.content.Context
import androidx.room.Room
import com.vignesh.todo.common.repository.local.db.TDDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object LNCommonRepositoryModuleTest {

    @Qualifier
    annotation class TDDataBaseTest

    @Provides
    @TDDataBaseTest
    fun getDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, TDDatabase::class.java).allowMainThreadQueries()
            .build()
}