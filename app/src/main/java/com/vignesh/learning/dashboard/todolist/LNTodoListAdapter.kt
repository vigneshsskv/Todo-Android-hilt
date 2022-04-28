package com.vignesh.learning.dashboard.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.learning.common.repository.model.LNTodoData
import com.vignesh.learning.databinding.LnItemTodoDataBinding

class LNTodoListAdapter(
    private val onClickListener: (data: LNTodoData) -> Unit
) :
    RecyclerView.Adapter<LNTodoListAdapter.LNTodoListViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<LNTodoData>() {
        override fun areItemsTheSame(oldItem: LNTodoData, newItem: LNTodoData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LNTodoData, newItem: LNTodoData) =
            oldItem == newItem
    })

    fun submitList(list: List<LNTodoData>) = differ.submitList(list)

    inner class LNTodoListViewHolder(val viewHolder: LnItemTodoDataBinding) :
        RecyclerView.ViewHolder(viewHolder.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LNTodoListViewHolder(
        LnItemTodoDataBinding.inflate(
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