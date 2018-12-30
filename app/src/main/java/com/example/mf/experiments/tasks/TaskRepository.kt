package com.example.mf.experiments.tasks

import com.example.mf.experiments.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository private constructor(private val taskDao: TaskDao){

    fun getTasks() = taskDao.getTasks()

    companion object {
        @Volatile private var instance: TaskRepository? = null
        fun getInstance(taskDao: TaskDao) =
            instance ?: synchronized(this) {
                instance
                    ?: TaskRepository(taskDao).also { instance = it }
            }
    }

    suspend fun createTask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.insertOne(task)
        }
    }
    suspend fun deleteTask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.deleteOne(task)
        }
    }

    suspend fun updateTask(vararg task: Task){
        for(t in task){
            withContext(Dispatchers.IO){
                taskDao.updateOne(t)
            }
        }
    }
}