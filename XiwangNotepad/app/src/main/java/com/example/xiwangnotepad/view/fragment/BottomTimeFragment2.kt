package com.example.xiwangnotepad.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.xiwangnotepad.databinding.FragmentTimeDialog2Binding
import com.example.xiwangnotepad.utils.getTime
import com.example.xiwangnotepad.viewmodel.BottomTimeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomTimeFragment2 : BottomSheetDialogFragment() {
    private var _binding: FragmentTimeDialog2Binding?=null
    private val binding: FragmentTimeDialog2Binding get() = _binding!!
    private lateinit var viewModel: BottomTimeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentTimeDialog2Binding.inflate(inflater,container,false)
        viewModel= ViewModelProvider(requireActivity())[BottomTimeViewModel::class.java]
        binding.time2BtnConfirm.setOnClickListener{
            viewModel.setTime(binding.time2DpTinytime.getTime())
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}