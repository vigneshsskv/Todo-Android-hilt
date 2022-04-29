package com.vignesh.todo.common.repository.local.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.vignesh.todo.common.dj.LNCommonRepositoryModuleTest
import com.vignesh.todo.common.repository.local.db.TDDatabase
import com.vignesh.todo.common.repository.model.TDTodoData
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
class TDTodoDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecuter = InstantTaskExecutorRule()

    @Inject
    @LNCommonRepositoryModuleTest.TDDataBaseTest
    lateinit var database: TDDatabase
    private lateinit var todoDao: TDTodoDao

    @Before
    fun setup() {
        hiltRule.inject()
        todoDao = database.getTodoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoData(
                name = "TDD",
                description = "Test driven Development",
                category = "Android",
                status = "Yet to start",
                reminder = "Daily",
                reminderTime = "1.00PM"
            )
            todoDao.insertTODO(todoData)
            val data = todoDao.fetchTodo().getOrAwaitValue()
            assertThat(data).contains(todoData)
        }
    }

    @Test
    fun updateTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoData(
                name = "TDD",
                description = "Test driven Development",
                category = "Android",
                status = "Yet to start",
                reminder = "Daily",
                reminderTime = "1.00PM"
            ).also {
                it.id = 1
            }
            todoDao.insertTODO(todoData)
            todoData.name = "TDD Android"
            todoDao.updateTODO(todoData)
            val data = todoDao.fetchTodo().getOrAwaitValue()
            assertThat(data).contains(todoData)
        }
    }

    @Test
    fun deleteTodoItem() = runTest {
        withContext(Dispatchers.Main) {
            val todoData = TDTodoData(
                name = "TDD",
                description = "Test driven Development",
                category = "Android",
                status = "Yet to start",
                reminder = "Daily",
                reminderTime = "1.00PM"
            ).also {
                it.id = 1
            }
            todoDao.insertTODO(todoData)
            todoDao.deleteTODO(todoData)
            val data = todoDao.fetchTodo().getOrAwaitValue()
            assertThat(data).doesNotContain(todoData)
        }
    }
}