package com.vignesh.todo.dashboard.edittodo

import androidx.lifecycle.LiveData
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
    private val _revert by lazy {
        MutableLiveData<Boolean>()
    }
    val revert: LiveData<Boolean> = _revert

    private val _saveOrEdit by lazy {
        SingleLiveEvent<Boolean>()
    }
    val saveOrEdit: LiveData<Boolean> = _saveOrEdit

    private val _errorNameHandle by lazy {
        MutableLiveData<String>()
    }
    val errorNameHandle: LiveData<String> = _errorNameHandle

    private val _errorDescriptionHandle by lazy {
        MutableLiveData<String>()
    }
    val errorDescriptionHandle: LiveData<String> = _errorDescriptionHandle

    private val _errorCategoryHandle by lazy {
        MutableLiveData<String>()
    }
    val errorCategoryHandle: LiveData<String> = _errorCategoryHandle

    private val _errorStatusHandle by lazy {
        MutableLiveData<String>()
    }
    val errorStatusHandle: LiveData<String> = _errorStatusHandle

    private val _errorReminderTypeHandle by lazy {
        MutableLiveData<String>()
    }
    val errorReminderTypeHandle: LiveData<String> = _errorReminderTypeHandle

    private val _errorReminderTimeHandle by lazy {
        MutableLiveData<String>()
    }
    val errorReminderTimeHandle: LiveData<String> = _errorReminderTimeHandle

    private val _errorNotEditTodoHandle by lazy {
        SingleLiveEvent<Boolean>()
    }
    val errorNotEditTodoHandle: LiveData<Boolean> = _errorNotEditTodoHandle

    var editTodoData: TDTodoData? = null
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
            _revert.value = false
        }
    }

    fun checkForChanges() {
        _revert.value = isChangeMade()
        validateField()
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

    private fun isChangeMade() = (TDEditTodoUtils.checkMade(name, editTodoData?.name)
            || TDEditTodoUtils.checkMade(description, editTodoData?.description)
            || TDEditTodoUtils.checkMade(category, editTodoData?.category)
            || TDEditTodoUtils.checkMade(status, editTodoData?.status)
            || TDEditTodoUtils.checkMade(reminder, editTodoData?.reminder)
            || TDEditTodoUtils.checkMade(reminderTime, editTodoData?.reminderTime)).also {
        _errorNotEditTodoHandle.value = it
    }

    private fun validateField(): Boolean {
        val emptyNameCheck = emptyCheck(name, _errorNameHandle)
        val emptyDescriptionCheck = emptyCheck(description, _errorDescriptionHandle)
        val emptyCategoryCheck = emptyCheck(category, _errorCategoryHandle)
        val emptyStatusCheck = emptyCheck(status, _errorStatusHandle)
        val emptyReminderCheck = emptyCheck(reminder, _errorReminderTypeHandle)
        val emptyReminderTimeCheck = emptyCheck(reminderTime, _errorReminderTimeHandle)
        return emptyNameCheck && emptyDescriptionCheck && emptyCategoryCheck && emptyStatusCheck && emptyReminderCheck && emptyReminderTimeCheck
    }

    private fun emptyCheck(value: String?, event: MutableLiveData<String>): Boolean {
        val result = TDEditTodoUtils.emptyCheck(value)
        event.value = if (result) "Can't be empty" else null
        return !result
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
        _saveOrEdit.value = true
    }

    private fun editTodo(todo: TDTodoData) {
        viewModelScope.launch {
            repository.update(todo)
        }
        _saveOrEdit.value = true
    }

    private fun deleteTodo(todo: TDTodoData) {
        viewModelScope.launch {
            repository.delete(todo)
        }
        _saveOrEdit.value = true
    }
}