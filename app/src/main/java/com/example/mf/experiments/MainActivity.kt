package com.example.mf.experiments

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mf.experiments.databinding.ActivityMainBinding
import com.example.mf.experiments.tasks.TaskListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val taskListFragment = TaskListFragment()
    private var toggle = Toggle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.test = toggle.state
        observe()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, taskListFragment).commit()
        floatingActionButton.setOnClickListener {
            taskListFragment.addList()
        }
    }

    fun toaster(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }

    private fun experiment() {
    }

    private fun observe() {
    }
}

class Toggle {
    var state = false

    init {
    }

    fun turn(): Toggle {
        state = !state
        return this
    }
}