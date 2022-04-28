package com.vignesh.learning.dashboard.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vignesh.learning.R
import com.vignesh.learning.common.LNFragment
import com.vignesh.learning.common.repository.model.LNTodoData
import com.vignesh.learning.dashboard.edittodo.LNEditTodoFragment
import com.vignesh.learning.databinding.LnFragmentTodoListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LNTodoListFragment : LNFragment() {
    private lateinit var todoListScreen: LnFragmentTodoListBinding

    private val viewModel by viewModels<LNTodoListViewModel>()

    private lateinit var adapter: LNTodoListAdapter

    companion object {
        @JvmStatic
        fun newInstance() = LNTodoListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = LnFragmentTodoListBinding.inflate(inflater, container, false).run {
        todoListScreen = this
        this.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(title = "Todo")
        setupRecyclerView()
        setClickListener()
        observeViewModel()
    }

    private fun setClickListener() {
        todoListScreen.fabAddTodo.setOnClickListener {
            moveToAddOrEditTodoScreen()
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            todoList.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun setupRecyclerView() = todoListScreen.rvTodoList.also {
        adapter = LNTodoListAdapter { data ->
            moveToAddOrEditTodoScreen(data)
        }
        it.adapter = adapter
        it.layoutManager = LinearLayoutManager(context)
    }

    private fun moveToAddOrEditTodoScreen(data: LNTodoData? = null) {
        replaceFragment(R.id.fl_dashboard, LNEditTodoFragment.newInstance(data), true)
    }
}