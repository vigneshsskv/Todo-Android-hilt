package com.vignesh.todo.dashboard.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vignesh.todo.R
import com.vignesh.todo.common.TDFragment
import com.vignesh.todo.common.repository.model.TDTodoData
import com.vignesh.todo.dashboard.edittodo.TDEditTodoFragment
import com.vignesh.todo.databinding.TdFragmentTodoListBinding


class TDTodoListFragment : TDFragment() {
    private lateinit var todoListScreen: TdFragmentTodoListBinding

    private val viewModel by viewModels<TDTodoListViewModel>()

    private lateinit var adapter: TDTodoListAdapter

    companion object {
        @JvmStatic
        fun newInstance() = TDTodoListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = TdFragmentTodoListBinding.inflate(inflater, container, false).run {
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
        adapter = TDTodoListAdapter { data ->
            moveToAddOrEditTodoScreen(data)
        }
        it.adapter = adapter
        it.layoutManager = LinearLayoutManager(context)
    }

    private fun moveToAddOrEditTodoScreen(data: TDTodoData? = null) {
        replaceFragment(R.id.fl_dashboard, TDEditTodoFragment.newInstance(data), true)
    }
}