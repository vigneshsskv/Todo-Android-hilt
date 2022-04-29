package com.vignesh.todo.common.repository.local.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.vignesh.todo.common.dj.LNCommonRepositoryModuleTest
import com.vignesh.todo.common.repository.local.db.TDDatabase
import com.vignesh.todo.common.repository.model.TDTodoCategoryData
import com.vignesh.todo.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class TDTodoCategoryDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @LNCommonRepositoryModuleTest.TDDataBaseTest
    lateinit var database: TDDatabase
    lateinit var categoryDao: TDTodoCategoryDao

    @Before
    fun setup() {
        hiltRule.inject()
        categoryDao = database.getTodoCategoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoCategoryData(
                name = "TDD",
            )
            categoryDao.insertTodoCategory(todoData)
            val data = categoryDao.fetchTodoCategory().getOrAwaitValue()
            Truth.assertThat(data).contains(todoData)
        }
    }

    @Test
    fun updateTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoCategoryData(
                name = "TDD",
            ).also {
                it.id = 1
            }
            categoryDao.insertTodoCategory(todoData)
            todoData.name = "TDD Android"
            categoryDao.updateTodoCategory(todoData)
            val data = categoryDao.fetchTodoCategory().getOrAwaitValue()
            Truth.assertThat(data).contains(todoData)
        }
    }

    @Test
    fun deleteTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoCategoryData(
                name = "TDD",
            ).also {
                it.id = 1
            }
            categoryDao.insertTodoCategory(todoData)
            categoryDao.deleteTodoCategory(todoData)
            val data = categoryDao.fetchTodoCategory().getOrAwaitValue()
            Truth.assertThat(data).doesNotContain(todoData)
        }
    }
}