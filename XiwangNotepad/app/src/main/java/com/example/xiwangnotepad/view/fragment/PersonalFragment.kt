package com.example.xiwangnotepad.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.xiwangnotepad.MainActivity
import com.example.xiwangnotepad.R
import com.example.xiwangnotepad.databinding.FragmentPersonalBinding
import com.example.xiwangnotepad.utils.SharedPreferenceUtil
import com.example.xiwangnotepad.view.activity.LoginActivity

class PersonalFragment : Fragment() {
    private var _binding: FragmentPersonalBinding? = null
    private val binding: FragmentPersonalBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        initEvent()
        initFAB()
        return binding.root
    }

    fun initEvent() {


    }
    fun initFAB() {
        when (SharedPreferenceUtil.loginDetection(requireContext())) {
            true -> {
                binding.personalFabLogin.setImageResource(R.drawable.ic_delete_iv)
                binding.personalFabLogin.setOnClickListener {
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("你确定要退出登录吗?")
                    dialog.setMessage("这会清除你的登录状态!")
                    dialog.setCancelable(false)
                    dialog.setPositiveButton("确认") { _, _ ->
                        SharedPreferenceUtil.initializationSP(requireContext())
                        val intent= Intent(requireContext(), MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    dialog.setNegativeButton("取消") { _, _ -> }
                    dialog.show()
                }
            }
            false -> {
                binding.personalFabLogin.setImageResource(R.drawable.ic_register_iv)
                binding.personalFabLogin.setOnClickListener {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}