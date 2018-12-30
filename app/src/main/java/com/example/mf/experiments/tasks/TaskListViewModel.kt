package com.example.mf.experiments.tasks

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.mf.experiments.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TaskListViewModel internal constructor(private val repository: TaskRepository): ViewModel(){
    private val taskList = MediatorLiveData<List<Task>>()
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)
    init {
        val liveTaskList = repository.getTasks()
        taskList.addSource(liveTaskList,taskList::setValue)
        addTask(Task(id = "1", detail = "Test", status = "Unfinished"))
    }
    fun getTasks() = taskList
    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.createTask(task)
        }
    }
    fun deleteTask(task: Task){
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
    fun updateTask(vararg task: Task){
        viewModelScope.launch {
            for(t in task) {
                repository.updateTask(t)
            }
        }
    }
}

