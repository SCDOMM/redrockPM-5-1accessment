package com.example.xiwangnotepad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.xiwangnotepad.repository.data.CardModel
import com.example.xiwangnotepad.utils.DataBaseClient
import kotlinx.coroutines.launch

/**   
 * 包名称： com.example.xiwangnotepad.viewmodel
 * 类名称：CreateViewModel
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-04 16:17
 *
 */
class CreateViewModel(application: Application) : AndroidViewModel(application) {
    private val cardDao = DataBaseClient.getDataBase(application).cardDao()
    fun postData(cardData: CardModel, onSuccess: () -> Unit,onFailed:()-> Unit) {
        try{
        viewModelScope.launch {
            cardDao.insertCard(cardData)
            onSuccess()
        }}catch (e: Exception){
            e.printStackTrace()
            onFailed()
        }
    }
}