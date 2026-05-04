package com.example.xiwangnotepad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.xiwangnotepad.repository.data.CardModel
import com.example.xiwangnotepad.utils.CardDao
import com.example.xiwangnotepad.utils.DataBaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**   
 * 包名称： com.example.xiwangnotepad.viewmodel
 * 类名称：HomeViewModel
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-03 16:45
 *
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var _liveData = MutableLiveData<ArrayList<CardModel>>()
    val liveData: LiveData<ArrayList<CardModel>> get() = _liveData
    private var cardDao: CardDao = DataBaseClient.getDataBase(application).cardDao()
    init {
        viewModelScope.launch {
         _liveData.value = cardDao.getAllCards() as ArrayList<CardModel>?
        }
    }
    fun refreshLiveData(){
        viewModelScope.launch {
            _liveData.value = cardDao.getAllCards() as ArrayList<CardModel>?
        }
    }
    fun moveItem(newOrderList: ArrayList<CardModel>){
        viewModelScope.launch(Dispatchers.IO) {
            newOrderList.forEachIndexed { index, card ->
                cardDao.updateSortOrder(card.id,index)
            }
        }
    }
    fun removeItem(position: Int) {
        val currentList = _liveData.value ?: return
        if (currentList.indices.contains(position)) {
            viewModelScope.launch {
                cardDao.deleteCard(currentList[position])
            }
            currentList.removeAt(position)
            _liveData.value = currentList
        }
    }

    fun createInstance(){
        viewModelScope.launch {
            for (i in 1..20){
                cardDao.insertCard(CardModel(0,i,(114514+i).toString(),1715286345000,""))
            }
        }
    }
}