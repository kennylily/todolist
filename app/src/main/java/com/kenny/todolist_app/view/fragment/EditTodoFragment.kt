package com.kenny.todolist_app.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kenny.todolist_app.MainActivity
import com.kenny.todolist_app.R
import com.kenny.todolist_app.databinding.FragmentEditTodoBinding
import com.kenny.todolist_app.databinding.FragmentTodoListBinding
import com.kenny.todolist_app.model.ToDoItem
import com.kenny.todolist_app.viewmodel.TodoViewDetailModel
import com.kenny.todolist_app.viewmodel.TodoViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTodoFragment : Fragment(),View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentEditTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewDetailModel by viewModels()
    private var int_taskID : Int = 0
    private var str_task: String = ""
    private var isCompleted: Boolean = false
    private var int_userID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener(this)
        binding.buttonDelete.setOnClickListener(this)
        viewModel.todoDetail.observe(viewLifecycleOwner) { todo ->
            // 更新 UI 元素
            binding.editTitle.setText(todo.todo)
            binding.checkboxCompletechecked.isChecked = todo.completed
            int_taskID = todo.id
            str_task = todo.todo
            isCompleted = todo.completed
            int_userID = todo.userId
        }
        viewModel.todoModifySuccess.observe(viewLifecycleOwner){ success ->
            if (success) {
                // 修改成功，返回上一頁
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                // 修改失敗，顯示錯誤訊息
                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                Log.e("EditTodoFragment", "修改任務失敗")
            }
        }
        viewModel.fetchToDosDetail(taskID = (requireContext() as MainActivity).get_taskID())

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button_save -> {
                // 保存修改
                val updatedTodo = binding.editTitle.text.toString()
                val isCompleted = binding.checkboxCompletechecked.isChecked
                Log.d("test","${int_taskID}")
                // 更新任務
                viewModel.updateTodoDetail(todoItem =
                    ToDoItem(
                        id = int_taskID,
                        todo = updatedTodo,
                        completed = isCompleted,
                        userId = int_userID
                    )
                )
                // 返回上一頁
                //requireActivity().onBackPressed()
            }
            R.id.button_delete -> {
                // 取消修改，返回上一頁
                //requireActivity().onBackPressed()
                // 刪除任務
                viewModel.deleteTodoDetail(taskID = int_taskID)
            }
        }
    }


}