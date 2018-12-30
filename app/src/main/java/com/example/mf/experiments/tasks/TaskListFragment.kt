package com.example.mf.experiments.tasks

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mf.experiments.InjectorUtils
import com.example.mf.experiments.R
import com.example.mf.experiments.Task
import com.example.mf.experiments.databinding.FragmentTaskListBinding
import kotlinx.android.synthetic.main.customdialog.view.*
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : Fragment(),TaskAdapter.ItemListener {
    override fun delete(task: Task) {
        viewModel.deleteTask(task)
        getLists()
    }

    override fun update(task: Task) {
        val customDialog = CustomDialog()
        customDialog.setListener(object : CustomDialog.SubmitListener {
            override fun submit(text: String) {
                task.status = text
                viewModel.updateTask(task)
                getLists()
                customDialog.dismiss()
            }
        })
        val fm = fragmentManager
        customDialog.show(fm!!,"")
    }

    private lateinit var viewModel: TaskListViewModel
    private val adapter = TaskAdapter(this)
    private var i = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentTaskListBinding>(
                inflater, R.layout.fragment_task_list, container, false
            )
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideTaskListViewModelFactory(context)


        binding.tasklist.layoutManager = LinearLayoutManager(context)
        binding.tasklist.adapter = adapter

        viewModel = ViewModelProviders.of(this, factory)[TaskListViewModel::class.java]
        getLists()
        return binding.root
    }

    private fun getLists() {
        viewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            adapter.submitList(tasks)
        })
    }

    fun addList(){
        viewModel.addTask(Task("${i+1}","Finished $i","What a twist $i"))
        i++
        getLists()
    }
}

class CustomDialog : DialogFragment(){
    lateinit var submitListener: SubmitListener
    fun setListener(submit: SubmitListener){
        submitListener = submit
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.customdialog,container,false)
        root.submit.setOnClickListener {
            submitListener.submit(root.editText.text.toString())
        }
        return root
    }
    interface SubmitListener{
        fun submit(text : String)
    }

}
