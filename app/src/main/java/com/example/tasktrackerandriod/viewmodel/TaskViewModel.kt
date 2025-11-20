package com.example.tasktrackerandriod.viewmodel

// Imports
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.tasktrackerandriod.data.TaskDataStore
import com.example.tasktrackerandriod.data.model.Task
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TaskViewModel(app: Application) : AndroidViewModel(app) {
    // Private properties
     private val dataStore = TaskDataStore(app)
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks
    private var nextId: Int = 1

    // Initialize the ViewModel
    init {
        viewModelScope.launch {
            dataStore.tasksFlow.collect { loaded ->
                _tasks.value = loaded
                nextId = loaded.maxOfOrNull { it.id }?.plus(1) ?: 1
            }

        }

    }

    // Helper function to update tasks
    private fun updateTasks(tasks: List<Task>) {
        _tasks.value = tasks
        viewModelScope.launch {
            dataStore.saveTasks(tasks)
        }
    }

    // Public functions
    // Add a new task
    fun addTask(title: String) {
        val newId = (tasks.value.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Task(id = newId, title = title)
        updateTasks(tasks.value + newTask)
    }

    // Toggle the completion status of a task
    fun toggleTaskComplete(id: Int) {
        val updated = tasks.value.map {
            if (it.id == id) it.copy(isCompleted = !it.isCompleted)
            else it
        }
        updateTasks(updated)
    }

    // Edit the title of a task
    fun editTask(id: Int, newTitle: String) {
        // FIX 1.1: Use a different variable name ('updatedTasks')
        val updatedTasks = tasks.value.map {
            if (it.id == id) it.copy(title = newTitle)
            else it
        }
        // FIX 1.2: Pass the correct variable to 'saveTasks'
        updateTasks(updatedTasks)
    }

    // Delete a task
    fun deleteTask(id: Int) {
        val remainingTasks = tasks.value.filterNot { it.id == id }
        updateTasks(remainingTasks)
    }
}