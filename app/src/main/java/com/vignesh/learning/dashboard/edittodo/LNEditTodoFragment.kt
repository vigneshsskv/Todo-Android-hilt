package com.vignesh.learning.dashboard.edittodo

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.vignesh.learning.R
import com.vignesh.learning.common.LNFragment
import com.vignesh.learning.common.repository.model.LNTodoData
import com.vignesh.learning.databinding.LnFragmentEditTodoBinding

class LNEditTodoFragment : LNFragment() {
    private lateinit var editTodoScreen: LnFragmentEditTodoBinding
    private lateinit var menuItem: Menu
    private val viewModel by viewModels<LNEditTodoViewModel>()

    companion object {
        private const val TODO_DATA = "todo_data_to_edit"
        fun newInstance(data: LNTodoData? = null) =
            LNEditTodoFragment().apply {
                arguments = bundleOf(
                    TODO_DATA to data?.let { Gson().toJson(data) }
                )
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = LnFragmentEditTodoBinding.inflate(inflater, container, false).run {
        editTodoScreen = this
        this.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ln_edit_todo, menu)
        menuItem = menu
        menuItem.findItem(R.id.mu_revert)?.let {
            it.isEnabled = false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mu_revert -> {
                viewModel.revertEditedChange()
                loadData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            arguments?.let {
                it.getString(TODO_DATA, null)?.let {
                    viewModel.loadData(Gson().fromJson(it, LNTodoData::class.java))
                }
            }
        }
        setToolbar(homeButton = true, title = if (viewModel.isEditTodo) "Edit" else "Add")
        loadData()
        setListener()
        observeViewModel()
    }

    private fun loadData() {
        with(viewModel) {
            editTodoScreen.apply {
                name?.let(tieTodoName::setText)
                description?.let(tieTodoDescription::setText)
                category?.let(actTodoCategory::setText)
                status?.let(actTodoStatus::setText)
                reminder?.let(actTodoReminderType::setText)
                reminderTime?.let(tieTodoReminderTime::setText)
            }
        }
    }

    private fun setListener() {
        with(editTodoScreen) {
            with(viewModel) {
                tieTodoName.addTextChangedListener { value ->
                    value?.let { name = it.toString() }
                    checkForChanges()
                }
                tieTodoDescription.addTextChangedListener { value ->
                    value?.let { description = it.toString() }
                    checkForChanges()
                }
                actTodoCategory.addTextChangedListener { value ->
                    value?.let { category = it.toString() }
                    checkForChanges()
                }
                actTodoStatus.addTextChangedListener { value ->
                    value?.let { status = it.toString() }
                    checkForChanges()
                }
                actTodoReminderType.addTextChangedListener { value ->
                    value?.let { reminder = it.toString() }
                    checkForChanges()
                }
                tieTodoReminderTime.addTextChangedListener { value ->
                    value?.let { reminderTime = it.toString() }
                    checkForChanges()
                }
                btnSave.setOnClickListener {
                    saveOrUpdateTodo()
                }
            }
        }
    }

    private fun observeViewModel() {
        with(editTodoScreen) {
            with(viewModel) {
                revert.observe(viewLifecycleOwner) { enable ->
                    if (::menuItem.isInitialized) {
                        menuItem.findItem(R.id.mu_revert)?.let {
                            it.isEnabled = enable
                        }
                    }
                }
                saveOrEdit.observe(viewLifecycleOwner) {
                    popCurrentFragment()
                }
                errorNameHandle.observe(viewLifecycleOwner, tilTodoName::setError)
                errorDescriptionHandle.observe(viewLifecycleOwner, tilTodoDescription::setError)
                errorCategoryHandle.observe(viewLifecycleOwner, tilTodoCategory::setError)
                errorStatusHandle.observe(viewLifecycleOwner, tilTodoStatus::setError)
                errorReminderTypeHandle.observe(viewLifecycleOwner, tilTodoReminderType::setError)
                errorReminderTimeHandle.observe(viewLifecycleOwner, tilTodoReminderTime::setError)
            }
        }
    }
}