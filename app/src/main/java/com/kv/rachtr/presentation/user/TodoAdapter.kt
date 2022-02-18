package com.kv.rachtr.presentation.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kv.rachtr.databinding.ItemTodoBinding
import com.kv.rachtr.domain.model.TodoModel

class TodoAdapter() :
    ListAdapter<TodoModel, TodoAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        var inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)

        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: TodoAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getObject(position: Int): TodoModel {
        return getItem(position)
    }

    inner class ItemViewholder(var binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoModel) = with(itemView) {
            binding.item = item
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<TodoModel>() {

    override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
        return oldItem?.id == newItem?.id

    }

    override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
        return oldItem == newItem

    }
}