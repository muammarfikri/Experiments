package com.example.mf.experiments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey @ColumnInfo(name = "id") val id : String,
    var status : String,
    val detail : String
)