package com.example.mf.experiments.tasks

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mf.experiments.Task

@Dao
interface TaskDao{
    @Query("SELECT * FROM task ORDER BY id")
    fun getTasks() : LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tasks : List<Task>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(tasks : Task)

    @Delete()
    fun deleteOne(tasks: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateOne(vararg task: Task)

//    @Query("UPDATE task SET status = :status WHERE id = :id")
//    fun updateOne(id: String,status : String = "Unfinished")
}