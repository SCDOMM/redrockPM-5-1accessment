package com.example.xiwangnotepad.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.xiwangnotepad.repository.data.CardModel
import com.example.xiwangnotepad.utils.CardDao
import com.example.xiwangnotepad.utils.DataBaseClient
import kotlinx.coroutines.launch

/**   
 * 包名称： com.example.xiwangnotepad.viewmodel
 * 类名称：HomeDetailViewHolder
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-03 23:51
 *
 */
class HomeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var _liveData = MutableLiveData<CardModel>()
    val liveData: LiveData<CardModel> get() = _liveData
    private var cardDao: CardDao = DataBaseClient.getDataBase(application).cardDao()
    fun initData(id:Int){
        viewModelScope.launch {
            val card = cardDao.selectCardByID(id)
            _liveData.postValue(card)
        }
    }
    fun updateCard(cardModel: CardModel) {
        viewModelScope.launch {
            cardDao.updateCard(cardModel)
        }
        _liveData.value=cardModel
        Log.d("name",cardModel.name)
    }
}
