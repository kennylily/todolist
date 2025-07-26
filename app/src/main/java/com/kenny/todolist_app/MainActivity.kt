package com.kenny.todolist_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.kenny.todolist_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var taskID : Int = 0
    private var is_should_reload = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setNavGraph()

    }
    override fun onResume() {
        super.onResume()
        // Initialize or set up any necessary components here
        Log.d("MainActivity", "onStart called")
        set_should_reload(true)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Clear binding reference to avoid memory leaks
        binding = null as ActivityMainBinding
    }
    private fun setNavGraph(){
        val navController = Navigation.findNavController(binding.navHostFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
    }
    public fun set_taskID(id: Int) {
        taskID = id
        Log.d("MainActivity", "Task ID set to: $taskID")
    }
    public fun set_should_reload(bool_should:Boolean){
        this.is_should_reload = bool_should
    }
    public fun get_taskID(): Int {
        Log.d("MainActivity", "Task ID get to: $taskID")
        return taskID
    }
    public fun get_should_reload(): Boolean {
        return this.is_should_reload
    }
}