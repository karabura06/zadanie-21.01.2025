package com.example.semesterassignment


import Task
import TaskAdapter
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semesterassignment.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTask: EditText
    private lateinit var buttonAddTask: Button
    private lateinit var recyclerViewTasks: RecyclerView
    private val tasks = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTask = findViewById(R.id.editTextTask)
        buttonAddTask = findViewById(R.id.buttonAddTask)
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)

        loadTasks()

        taskAdapter = TaskAdapter(tasks) { task ->
            showDeleteConfirmation(task)
        }

        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter

        buttonAddTask.setOnClickListener {
            val taskTitle = editTextTask.text.toString()
            if (taskTitle.isNotEmpty()) {
                tasks.add(Task(taskTitle))
                taskAdapter.notifyDataSetChanged()
                editTextTask.text.clear()
                saveTasks()
            }
        }
    }

    private fun showDeleteConfirmation(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Удаление задачи")
            .setMessage("Вы уверены, что хотите удалить эту задачу?")
            .setPositiveButton("Да") { _, _ ->
                tasks.remove(task)
                taskAdapter.notifyDataSetChanged()
                saveTasks()
            }
            .setNegativeButton("Нет", null)
            .show()
    }

    private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(tasks)
        editor.putString("tasks", json)
        editor.apply()
    }

    private fun loadTasks() {
        val sharedPreferences = getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("tasks", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            tasks.addAll(Gson().fromJson(json, type))
        }
    }
}
