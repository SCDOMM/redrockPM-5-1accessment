package com.example.xiwangnotepad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.xiwangnotepad.databinding.FragmentTimeDialogBinding
import com.example.xiwangnotepad.repository.data.YmdHmTimeModel
import com.example.xiwangnotepad.viewmodel.BottomTimeViewModel
import com.example.xiwangnotepad.viewmodel.BottomTimeViewModel2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**   
 * 包名称： com.example.xiwangnotepad.view.fragment
 * 类名称：BottomDialogFragment
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-04 00:28
 *
 */
class BottomTimeFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTimeDialogBinding? = null
    private val binding: FragmentTimeDialogBinding get() = _binding!!
    private lateinit var viewModel: BottomTimeViewModel
    private lateinit var viewModel2: BottomTimeViewModel2
    private var hour=0
    private var minute=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeDialogBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[BottomTimeViewModel::class.java]
        viewModel2= ViewModelProvider(requireActivity())[BottomTimeViewModel2::class.java]
        binding.timeTvTinytime.setOnClickListener {
            val bottomSheet = BottomTimeFragment2()
            bottomSheet.show(parentFragmentManager, "TimeDialog2")
        }
        binding.timeBtnConfirm.setOnClickListener {
            viewModel2.setDate(YmdHmTimeModel(binding.timeDpDate.year,binding.timeDpDate.month,binding.timeDpDate.dayOfMonth,hour,minute))
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
        initObserver()
        return binding.root
    }

    fun initObserver() {
        viewModel.liveData.observe(viewLifecycleOwner) { data ->
            hour=data.hour
            minute=data.minute
            val text: String = data.hour.toString() + "时" + data.minute.toString() + "分"
            binding.timeTvTinytime.text = text
        }
    }
}