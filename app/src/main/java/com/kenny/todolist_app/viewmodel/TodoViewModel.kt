package com.kenny.todolist_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.todolist_app.TodoDao
import com.kenny.todolist_app.apimanager.RetrofitInstance
import com.kenny.todolist_app.model.AppDatabase
import com.kenny.todolist_app.model.ToDoItem
import com.kenny.todolist_app.model.Todo
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class TodoViewModel(application: Application) : AndroidViewModel(application){
    private val _todoList = MutableLiveData<List<ToDoItem>>()
    val todoList: LiveData<List<ToDoItem>> get() = _todoList

    private val todoDao: TodoDao = AppDatabase.getDatabase(application).todoDao()


    fun fetchToDos(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getToDos()
                _todoList.value = response.todos.map {
                    ToDoItem(it.id, it.todo, it.completed, it.userId)
                }
                for (todo in response.todos) {
                    insert(
                        Todo(
                            id = todo.id,
                            todo = todo.todo,
                            completed = todo.completed,
                            userId = todo.userId
                        )
                    )
                }
            } catch (e: Exception) {
                // 處理錯誤
                Log.e("TodoViewModel", "Error fetching todos: ${e.message}")
            }
        }
    }

    // 插入待辦事項
    fun insert(todo: Todo) {
        viewModelScope.launch {
            todoDao.insert(todo)
        }
    }

    // 更新待辦事項
    fun update(todo: Todo) {
        viewModelScope.launch {
            todoDao.update(todo)
        }
    }

    // 獲取所有待辦事項
    fun getAllTodos() {
        viewModelScope.launch {
            val todos = todoDao.getAllTodos()
            _todoList.value = todos.map { ToDoItem(it.id, it.todo, it.completed, it.userId) }
        }
    }
}