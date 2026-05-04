package com.example.xiwangnotepad.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xiwangnotepad.databinding.FragmentHomeBinding
import com.example.xiwangnotepad.repository.data.CardModel
import com.example.xiwangnotepad.view.ItemTouchHelperCallback
import com.example.xiwangnotepad.view.ItemTouchMoveListener
import com.example.xiwangnotepad.view.activity.HomeDetailActivity
import com.example.xiwangnotepad.view.adapter.HomeAdapter
import com.example.xiwangnotepad.view.adapter.HomeOnClickListener
import com.example.xiwangnotepad.viewmodel.HomeViewModel

class HomeFragment : Fragment(), ItemTouchMoveListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private val forActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            viewModel.refreshLiveData()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        initEvent()
        initRefresh()
        return binding.root
    }

    fun initEvent() {
        adapter = HomeAdapter(requireContext())
        adapter.onClickListener = object : HomeOnClickListener {
            override fun onClick(data: CardModel) {
                var intent = Intent(requireContext(), HomeDetailActivity::class.java)
                intent.putExtra("id", data.id)
                forActivityResult.launch(intent)
            }
        }
        binding.homeRvDefault.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRvDefault.adapter = adapter
        val callback = ItemTouchHelperCallback(this)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.homeRvDefault)

        viewModel.liveData.observe(viewLifecycleOwner) { data ->
            adapter.submitArrayList(data)
        }
    }
    fun initRefresh(){
        binding.homeSrlDefault.setOnRefreshListener {
            viewModel.refreshLiveData()
            binding.homeSrlDefault.isRefreshing=false
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        adapter.moveItem(fromPosition, toPosition)
        return true
    }

    override fun onItemRemove(position: Int): Boolean {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("你确定要删除该任务?")
        dialog.setMessage("这会清除您的任务状态!")
        dialog.setCancelable(false)
        dialog.setPositiveButton("确认") { _, _ ->
            viewModel.removeItem(position)
        }
        dialog.setNegativeButton("取消") { _, _ ->
            adapter.notifyItemChanged(position)
        }
        dialog.show()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemComplete() {
        val orderList=adapter.getArrayList()
        viewModel.moveItem(orderList)
    }
}