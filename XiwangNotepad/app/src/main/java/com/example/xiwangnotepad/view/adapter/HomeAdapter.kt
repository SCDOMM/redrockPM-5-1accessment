package com.example.xiwangnotepad.view.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xiwangnotepad.databinding.FragmentHomeItemBinding
import com.example.xiwangnotepad.repository.data.CardModel
import com.example.xiwangnotepad.utils.toYmdHm

/**   
 * 包名称： com.example.xiwangnotepad.view.adapter
 * 类名称：HomeAdapter
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-03 12:30
 *
 */
class HomeAdapter(val context: Context) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
    lateinit var onClickListener: HomeOnClickListener
    private var dataList= ArrayList<CardModel>()
    fun submitArrayList(list: ArrayList<CardModel>){
        dataList=list
        notifyDataSetChanged()
    }
    fun getArrayList(): ArrayList<CardModel> {return ArrayList(dataList) }
    inner class ViewHolder(private val binding: FragmentHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected: Boolean = false
        init {
            itemView.setOnClickListener { _->
                val pos = bindingAdapterPosition
                onClickListener.onClick(dataList[pos])
            }
        }
        fun bindData(data: CardModel) {
            binding.homeItemTVDescription.text = data.name
            binding.homeItemTvTime.text=data.timeSamp.toYmdHm()
            binding.homeItemCBConfirm.setOnClickListener {
                if (!isSelected) {
                    isSelected = true
                    binding.homeItemTVDescription.paintFlags =
                        binding.homeItemTVDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    isSelected = false
                    binding.homeItemTVDescription.paintFlags =
                        binding.homeItemTVDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }
    fun moveItem(fromPosition:Int,toPosition:Int){
        if (fromPosition==toPosition)return
        val item = dataList.removeAt(fromPosition)
        dataList.add(toPosition, item)
       notifyItemMoved(fromPosition,toPosition)
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding = FragmentHomeItemBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindData(dataList[p1])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

interface HomeOnClickListener {
    fun onClick(data: CardModel)
}