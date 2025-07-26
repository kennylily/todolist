package com.kenny.todolist_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.todolist_app.apimanager.RetrofitInstance
import com.kenny.todolist_app.model.ToDoItem
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val _todoList = MutableLiveData<List<ToDoItem>>()
    val todoList: LiveData<List<ToDoItem>> get() = _todoList

    fun fetchToDos(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getToDos()
                _todoList.value = response.todos
            } catch (e: Exception) {
                // 處理錯誤
                Log.e("TodoViewModel", "Error fetching todos: ${e.message}")
            }
        }
    }
}