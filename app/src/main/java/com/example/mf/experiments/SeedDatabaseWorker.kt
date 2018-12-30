package com.example.mf.experiments

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SeedDatabaseWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        return try {
            AppDatabase.getInstance(applicationContext).taskDao().insertAll(
                listOf(
                    Task("1", "Unfinished", "Empty"),
                    Task("2", "Unfinished", "Empty"),
                    Task("3", "Unfinished", "Empty")
                )
            )
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}