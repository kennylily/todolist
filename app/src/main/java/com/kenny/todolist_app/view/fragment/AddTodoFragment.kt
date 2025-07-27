package com.kenny.todolist_app.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kenny.todolist_app.R
import com.kenny.todolist_app.databinding.FragmentAddTodoBinding
import com.kenny.todolist_app.databinding.FragmentEditTodoBinding
import com.kenny.todolist_app.model.ToDoItem
import com.kenny.todolist_app.model.ToDoItemInsertReq
import com.kenny.todolist_app.viewmodel.TodoNewViewModel
import com.kenny.todolist_app.viewmodel.TodoViewDetailModel


class AddTodoFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoNewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener(this)
        binding.textViewBack.setOnClickListener(this)
        viewModel.todoDetail.observe(viewLifecycleOwner) { todo ->
            // 更新 UI 元素
            binding.editTitle.setText(todo.todo)
            binding.checkboxCompletechecked.isChecked = todo.completed
        }
        viewModel.todoModifySuccess.observe(viewLifecycleOwner){ success ->
            if (success) {
                // 修改成功，返回上一頁
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                // 修改失敗，顯示錯誤訊息
                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                Log.e("AddTodoFragment", "新增任務失敗")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.textView_back->{
                requireActivity().onBackPressed()
            }
            R.id.button_save -> {
                var todoText = binding.editTitle.text.toString()
                var isCompleted = binding.checkboxCompletechecked.isChecked
                Log.d("AddTodoFragment", "todoText: $todoText, isCompleted: $isCompleted")
                if (todoText.isNotEmpty()) {
                    viewModel.insertToDoDetail(
                        ToDoItemInsertReq(
                            todoText,
                            isCompleted,
                            1
                        )
                    ) // 假設 userID 為 1
                    // 返回上一頁
                    //requireActivity().onBackPressed()
                } else {
                    // 顯示錯誤提示
                    binding.editTitle.error = "請輸入任務內容"
                }
            }
        }
    }
}