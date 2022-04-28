package com.vignesh.learning.dashboard.edittodo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.learning.common.SingleLiveEvent
import com.vignesh.learning.common.repository.LNRepositoryImpl
import com.vignesh.learning.common.repository.model.LNTodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LNEditTodoViewModel @Inject constructor(private val repository: LNRepositoryImpl<LNTodoData>) :
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

    private var editTodoData: LNTodoData? = null
    var name: String? = ""
    var description: String? = ""
    var category: String? = ""
    var status: String? = ""
    var reminder: String? = ""
    var reminderTime: String? = ""
    var isEditTodo: Boolean = false

    fun loadData(data: LNTodoData?) {
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
                        this@LNEditTodoViewModel.name?.let {
                            name = it
                        }
                        this@LNEditTodoViewModel.description?.let {
                            description = it
                        }
                        this@LNEditTodoViewModel.category?.let {
                            category = it
                        }
                        this@LNEditTodoViewModel.status?.let {
                            status = it
                        }
                        this@LNEditTodoViewModel.reminder?.let {
                            reminder = it
                        }
                        this@LNEditTodoViewModel.reminderTime?.let {
                            reminderTime = it
                        }
                        updatedAt = Date().time
                    })
                }
            } else {
                insertTodo(
                    LNTodoData(
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

    private fun initTodoData(data: LNTodoData?) {
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

    private fun insertTodo(todo: LNTodoData) {
        viewModelScope.launch {
            repository.insert(todo)
        }
        saveOrEdit.value = true
    }

    private fun editTodo(todo: LNTodoData) {
        viewModelScope.launch {
            repository.update(todo)
        }
        saveOrEdit.value = true
    }

    private fun deleteTodo(todo: LNTodoData) {
        viewModelScope.launch {
            repository.delete(todo)
        }
        saveOrEdit.value = true
    }
}