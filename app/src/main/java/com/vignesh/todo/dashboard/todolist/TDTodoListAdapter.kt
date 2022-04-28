package com.vignesh.todo.dashboard.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.todo.common.repository.model.TDTodoData
import com.vignesh.todo.databinding.TdItemTodoDataBinding

class TDTodoListAdapter(
    private val onClickListener: (data: TDTodoData) -> Unit
) :
    RecyclerView.Adapter<TDTodoListAdapter.LNTodoListViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<TDTodoData>() {
        override fun areItemsTheSame(oldItem: TDTodoData, newItem: TDTodoData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TDTodoData, newItem: TDTodoData) =
            oldItem == newItem
    })

    fun submitList(list: List<TDTodoData>) = differ.submitList(list)

    inner class LNTodoListViewHolder(val viewHolder: TdItemTodoDataBinding) :
        RecyclerView.ViewHolder(viewHolder.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LNTodoListViewHolder(
        TdItemTodoDataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: LNTodoListViewHolder, position: Int) {
        differ.currentList[position]?.run {
            with(holder) {
                itemView.setOnClickListener {
                    onClickListener(this@run)
                }
                viewHolder.apply {
                    tvTodoName.text = name
                    tvTodoCategory.text = category
                    tvTodoDescription.text = description
                    tvTodoReminderType.text = reminder
                    tvTodoReminderTime.text = reminderTime
                    tvTodoStatus.text = status
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size
}