package com.vignesh.todo.common.repository.local.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.vignesh.todo.common.dj.LNCommonRepositoryModuleTest
import com.vignesh.todo.common.repository.local.db.TDDatabase
import com.vignesh.todo.common.repository.model.TDTodoStatusData
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
class TDTodoStatusDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @LNCommonRepositoryModuleTest.TDDataBaseTest
    lateinit var database: TDDatabase
    lateinit var statusDao: TDTodoStatusDao

    @Before
    fun setup() {
        hiltRule.inject()
        statusDao = database.getTodoStatusDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoStatusData(
                name = "TDD",
            )
            statusDao.insertTodoStatus(todoData)
            val data = statusDao.fetchTodoStatus().getOrAwaitValue()
            Truth.assertThat(data).contains(todoData)
        }
    }

    @Test
    fun updateTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoStatusData(
                name = "TDD",
            ).also {
                it.id = 1
            }
            statusDao.insertTodoStatus(todoData)
            todoData.name = "TDD Android"
            statusDao.updateTodoStatus(todoData)
            val data = statusDao.fetchTodoStatus().getOrAwaitValue()
            Truth.assertThat(data).contains(todoData)
        }
    }

    @Test
    fun deleteTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoStatusData(
                name = "TDD",
            ).also {
                it.id = 1
            }
            statusDao.insertTodoStatus(todoData)
            statusDao.deleteTodoStatus(todoData)
            val data = statusDao.fetchTodoStatus().getOrAwaitValue()
            Truth.assertThat(data).doesNotContain(todoData)
        }
    }
}