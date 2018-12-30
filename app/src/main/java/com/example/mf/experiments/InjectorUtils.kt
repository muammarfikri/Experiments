package com.example.mf.experiments

import android.content.Context
import com.example.mf.experiments.tasks.TaskListViewModelFactory
import com.example.mf.experiments.tasks.TaskRepository

object InjectorUtils{
    fun provideTaskListViewModelFactory(context: Context): TaskListViewModelFactory {
        val repository = getTasksRepository(context)
        return TaskListViewModelFactory(repository)
    }
    private fun getTasksRepository(context: Context) : TaskRepository {
        return TaskRepository.getInstance(
            AppDatabase.getInstance(
                context
            ).taskDao()
        )
    }
}