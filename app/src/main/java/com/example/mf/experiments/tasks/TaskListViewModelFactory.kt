package com.example.mf.experiments.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskListViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TaskListViewModel(
        repository
    ) as T
}