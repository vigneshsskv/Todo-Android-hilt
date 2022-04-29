package com.vignesh.todo.dashboard.edittodo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.vignesh.todo.common.repository.TDTodoRepositoryTest
import com.vignesh.todo.common.repository.model.TDTodoData
import com.vignesh.todo.dashboard.TDMainCoroutineRule
import com.vignesh.todo.dashboard.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class TDEditTodoViewModelTest {

    private lateinit var viewModel: TDEditTodoViewModel
    private var errorNameHandle: String? = null
    private var errorDescriptionHandle: String? = null
    private var errorCategoryHandle: String? = null
    private var errorStatusHandle: String? = null
    private var errorReminderTypeHandle: String? = null
    private var errorReminderTimeHandle: String? = null

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TDMainCoroutineRule()

    @Before
    fun setup() {
        viewModel = TDEditTodoViewModel(repository = TDTodoRepositoryTest())
    }

    private fun utilValueAssign() {
        errorNameHandle = viewModel.errorNameHandle.getOrAwaitValue()
        errorDescriptionHandle = viewModel.errorDescriptionHandle.getOrAwaitValue()
        errorCategoryHandle = viewModel.errorCategoryHandle.getOrAwaitValue()
        errorStatusHandle = viewModel.errorStatusHandle.getOrAwaitValue()
        errorReminderTypeHandle = viewModel.errorReminderTypeHandle.getOrAwaitValue()
        errorReminderTimeHandle = viewModel.errorReminderTimeHandle.getOrAwaitValue()
    }

    @After
    fun tearDown() {
        errorNameHandle = null
        errorDescriptionHandle = null
        errorCategoryHandle = null
        errorStatusHandle = null
        errorReminderTypeHandle = null
        errorReminderTimeHandle = null
    }

    @Test
    fun `null value for all field excepted Can't be empty`() {
        viewModel.saveOrUpdateTodo()
        utilValueAssign()
        Truth.assertThat(errorNameHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorDescriptionHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorCategoryHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorStatusHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorReminderTypeHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorReminderTimeHandle).isEqualTo("Can't be empty")
    }

    @Test
    fun `empty string value for all excepted Can't be empty in all field`() {
        viewModel.name = ""
        viewModel.description = ""
        viewModel.category = ""
        viewModel.status = ""
        viewModel.reminder = ""
        viewModel.reminderTime = ""
        viewModel.saveOrUpdateTodo()
        utilValueAssign()
        Truth.assertThat(errorNameHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorDescriptionHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorCategoryHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorStatusHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorReminderTypeHandle).isEqualTo("Can't be empty")
        Truth.assertThat(errorReminderTimeHandle).isEqualTo("Can't be empty")
    }

    @Test
    fun `Valid string value for all excepted null is error field`() {
        viewModel.name = "TDD"
        viewModel.description = "Test Driven Development"
        viewModel.category = "Android Development"
        viewModel.status = "Yet to start"
        viewModel.reminder = "Daily"
        viewModel.reminderTime = "2.00PM"
        viewModel.saveOrUpdateTodo()
        utilValueAssign()
        Truth.assertThat(errorNameHandle).isEqualTo(null)
        Truth.assertThat(errorDescriptionHandle).isEqualTo(null)
        Truth.assertThat(errorCategoryHandle).isEqualTo(null)
        Truth.assertThat(errorStatusHandle).isEqualTo(null)
        Truth.assertThat(errorReminderTypeHandle).isEqualTo(null)
        Truth.assertThat(errorReminderTimeHandle).isEqualTo(null)
        val result = viewModel.saveOrEdit.getOrAwaitValue()
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `Valid string but not edit exist todo excepted changes`() {
        viewModel.isEditTodo = true
        viewModel.editTodoData = TDTodoData(
            name = "TDD",
            description = "Test Driven Development",
            category = "Android Development",
            status = "Yet to start",
            reminder = "Daily",
            reminderTime = "2.00PM",
        )
        viewModel.name = "TDD"
        viewModel.description = "Test Driven Development"
        viewModel.category = "Android Development"
        viewModel.status = "Yet to start"
        viewModel.reminder = "Daily"
        viewModel.reminderTime = "2.00PM"
        viewModel.saveOrUpdateTodo()
        val result = viewModel.errorNotEditTodoHandle.getOrAwaitValue()
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `Valid string and edit exist todo excepted changes`() {
        viewModel.isEditTodo = true
        viewModel.editTodoData = TDTodoData(
            name = "TDD Andriod",
            description = "Test Driven Development",
            category = "Android Development",
            status = "Yet to start",
            reminder = "Daily",
            reminderTime = "2.00PM",
        )
        viewModel.name = "TDD"
        viewModel.description = "Test Driven Development"
        viewModel.category = "Android Development"
        viewModel.status = "Yet to start"
        viewModel.reminder = "Daily"
        viewModel.reminderTime = "2.00PM"
        viewModel.saveOrUpdateTodo()
        val result = viewModel.saveOrEdit.getOrAwaitValue()
        Truth.assertThat(result).isTrue()
    }
}