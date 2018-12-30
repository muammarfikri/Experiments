package com.example.mf.experiments.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mf.experiments.Task
import com.example.mf.experiments.databinding.ListItemTaskBinding

class TaskAdapter(private val listener: ItemListener) : ListAdapter<Task, TaskAdapter.ViewHolder>(
    TaskDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        holder.apply {
            bind(task)
        }
    }

    class ViewHolder(
        private val binding: ListItemTaskBinding,
        private val listener: ItemListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.apply {
                task = item
                this.click = View.OnClickListener {
                    item.status = "Stalled"
                    listener.update(item)
                }
            }
        }
    }

    interface ItemListener {
        fun delete(task: Task)
        fun update(task: Task)
    }
}