package com.example.xiwangnotepad.view.fragment

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.xiwangnotepad.databinding.FragmentCreateBinding
import com.example.xiwangnotepad.repository.data.CardModel
import com.example.xiwangnotepad.utils.showToast
import com.example.xiwangnotepad.utils.toYmdHm
import com.example.xiwangnotepad.viewmodel.BottomTimeViewModel2
import com.example.xiwangnotepad.viewmodel.CreateViewModel

class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding: FragmentCreateBinding get() = _binding!!
    private lateinit var viewModel: CreateViewModel
    private lateinit var viewModel2: BottomTimeViewModel2
    private var timeSamp:Long=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentCreateBinding.inflate(inflater,container,false)
        viewModel= ViewModelProvider(this)[CreateViewModel::class.java]
        viewModel2= ViewModelProvider(requireActivity())[BottomTimeViewModel2::class.java]
        initEvent()
        return binding.root
    }

    fun initEvent(){
        binding.createEtvTime.setOnClickListener {
            initTime()
        }
        binding.createBtnConfirm.setOnClickListener {
            val cardData= CardModel(0,0,binding.createEtvName.text.toString(),timeSamp,binding.createEtvRemark.text.toString())
            viewModel.postData(cardData,{
                showToast(requireContext(),"保存成功！")
                binding.createEtvName.setText("")
                binding.createEtvRemark.setText("")
                binding.createEtvTime.text = ""
                timeSamp=0
            },{
                showToast(requireContext(),"保存失败！请重试！")
            })
        }
    }
    fun initTime(){
        val bottomSheet = BottomTimeFragment()
        bottomSheet.show(parentFragmentManager, "TimeDialog")
        viewModel2.liveData.observe(viewLifecycleOwner) { time ->
            val calendar = Calendar.getInstance().apply {
                set(time.year, time.month, time.day, time.hour, time.minute, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }
            val timeData = calendar.timeInMillis
            timeSamp = timeData
            binding.createEtvTime.text = timeData.toYmdHm()
        }
    }
}