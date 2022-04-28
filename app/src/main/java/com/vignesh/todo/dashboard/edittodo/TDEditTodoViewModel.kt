package com.vignesh.todo.dashboard.edittodo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.todo.common.SingleLiveEvent
import com.vignesh.todo.common.repository.TDRepositoryImpl
import com.vignesh.todo.common.repository.model.TDTodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TDEditTodoViewModel @Inject constructor(private val repository: TDRepositoryImpl<TDTodoData>) :
    ViewModel() {
    val revert by lazy {
        MutableLiveData<Boolean>()
    }
    val saveOrEdit by lazy {
        SingleLiveEvent<Boolean>()
    }
    val errorNameHandle by lazy {
        MutableLiveData<String>()
    }
    val errorDescriptionHandle by lazy {
        MutableLiveData<String>()
    }
    val errorCategoryHandle by lazy {
        MutableLiveData<String>()
    }
    val errorStatusHandle by lazy {
        MutableLiveData<String>()
    }
    val errorReminderTypeHandle by lazy {
        MutableLiveData<String>()
    }
    val errorReminderTimeHandle by lazy {
        MutableLiveData<String>()
    }

    private var editTodoData: TDTodoData? = null
    var name: String? = ""
    var description: String? = ""
    var category: String? = ""
    var status: String? = ""
    var reminder: String? = ""
    var reminderTime: String? = ""
    var isEditTodo: Boolean = false

    fun loadData(data: TDTodoData?) {
        editTodoData = data
        isEditTodo = data != null
        initTodoData(data)
    }

    fun revertEditedChange() {
        initTodoData(editTodoData)
        if (isEditTodo) {
            revert.value = false
        }
    }

    fun checkForChanges() {
        revert.value = isChangeMade()
        validateField()
    }

    private fun isChangeMade() = (checkMade(name, editTodoData?.name)
            || checkMade(description, editTodoData?.description)
            || checkMade(category, editTodoData?.category)
            || checkMade(status, editTodoData?.status)
            || checkMade(reminder, editTodoData?.reminder)
            || checkMade(reminderTime, editTodoData?.reminderTime))

    private fun validateField(): Boolean {
        val emptyNameCheck = emptyCheck(name, errorNameHandle)
        val emptyDescriptionCheck = emptyCheck(description, errorDescriptionHandle)
        val emptyCategoryCheck = emptyCheck(category, errorCategoryHandle)
        val emptyStatusCheck = emptyCheck(status, errorStatusHandle)
        val emptyReminderCheck = emptyCheck(reminder, errorReminderTypeHandle)
        val emptyReminderTimeCheck = emptyCheck(reminderTime, errorReminderTimeHandle)
        return emptyNameCheck && emptyDescriptionCheck && emptyCategoryCheck && emptyStatusCheck && emptyReminderCheck && emptyReminderTimeCheck
    }

    private fun checkMade(oldValue: String?, newValue: String?) = oldValue != newValue

    private fun emptyCheck(value: String?, event: MutableLiveData<String>): Boolean {
        val result = value == null || value.trim().isEmpty()
        event.value = if (result) "Can't be empty" else null
        return !result
    }

    fun saveOrUpdateTodo() {
        if (validateField() && isChangeMade()) {
            if (isEditTodo) {
                editTodoData?.let { data ->
                    editTodo(data.apply {
                        this@TDEditTodoViewModel.name?.let {
                            name = it
                        }
                        this@TDEditTodoViewModel.description?.let {
                            description = it
                        }
                        this@TDEditTodoViewModel.category?.let {
                            category = it
                        }
                        this@TDEditTodoViewModel.status?.let {
                            status = it
                        }
                        this@TDEditTodoViewModel.reminder?.let {
                            reminder = it
                        }
                        this@TDEditTodoViewModel.reminderTime?.let {
                            reminderTime = it
                        }
                        updatedAt = Date().time
                    })
                }
            } else {
                insertTodo(
                    TDTodoData(
                        name = name,
                        description = description,
                        category = category,
                        status = status,
                        reminder = reminder,
                        reminderTime = reminderTime,
                    )
                )
            }
        }
    }

    private fun initTodoData(data: TDTodoData?) {
        data.let {
            name = it?.name ?: ""
            description = it?.description ?: ""
            category = it?.category ?: ""
            status = it?.status ?: ""
            reminder = it?.reminder ?: ""
            reminderTime = it?.reminderTime ?: ""
        }
        checkForChanges()
    }

    private fun insertTodo(todo: TDTodoData) {
        viewModelScope.launch {
            repository.insert(todo)
        }
        saveOrEdit.value = true
    }

    private fun editTodo(todo: TDTodoData) {
        viewModelScope.launch {
            repository.update(todo)
        }
        saveOrEdit.value = true
    }

    private fun deleteTodo(todo: TDTodoData) {
        viewModelScope.launch {
            repository.delete(todo)
        }
        saveOrEdit.value = true
    }
}