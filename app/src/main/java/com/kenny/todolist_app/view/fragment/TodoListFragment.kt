package com.kenny.todolist_app.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenny.todolist_app.MainActivity
import com.kenny.todolist_app.R
import com.kenny.todolist_app.adapter.ToDoAdapter
import com.kenny.todolist_app.databinding.FragmentTodoListBinding
import com.kenny.todolist_app.model.ToDoItem
import com.kenny.todolist_app.viewmodel.TodoViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ToDoAdapter
    private val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 設置 RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        // 觀察 ViewModel 的 todoList
        viewModel.todoList.observe(viewLifecycleOwner) { todos ->
            adapter = ToDoAdapter(todos) {
                    todoItem ->
                // 在這裡處理點擊事件
                Toast.makeText(requireContext(), "點擊了: ${todoItem.id}", Toast.LENGTH_SHORT).show()
                (requireContext() as MainActivity).set_taskID(todoItem.id)
                // 導航到編輯任務頁面
                findNavController().navigate(R.id.action_to_edit)
            }
            binding.recyclerView.adapter = adapter
        }

        viewModel.fetchToDos()
        binding.buttonAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_to_add)
        }



    }

    override fun onResume() {
        super.onResume()
        // 發起 API 請求
        /*if(MainActivity().get_should_reload()) {
            viewModel.fetchToDos()
            MainActivity().set_should_reload(bool_should = false)
        }*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}