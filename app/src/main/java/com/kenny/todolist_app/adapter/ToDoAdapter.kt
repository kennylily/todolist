package com.kenny.todolist_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kenny.todolist_app.R
import com.kenny.todolist_app.databinding.RecycleTodolistBinding
import com.kenny.todolist_app.model.ToDoItem

class ToDoAdapter(
    private val todoList: List<ToDoItem>,
    private val onItemClick: (ToDoItem) -> Unit // 接收點擊事件的函數
) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    class ViewHolder(private val binding: RecycleTodolistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: ToDoItem, onItemClick: (ToDoItem) -> Unit) {
            binding.textViewTodoTitle.text = todoItem.todo
            binding.checkboxTodo.isChecked = todoItem.completed
            itemView.setOnClickListener {
                // Handle item click, e.g., toggle completion status
                onItemClick(todoItem) // 當項目被點擊時，調用傳入的函數
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecycleTodolistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoItem = todoList[position]
        holder.bind(todoItem, onItemClick)
    }

    override fun getItemCount(): Int = todoList.size
}
