package com.example.mf.experiments.tasks

import androidx.recyclerview.widget.DiffUtil
import com.example.mf.experiments.Task

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem
}