package com.example.xiwangnotepad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xiwangnotepad.repository.data.HmTimeModel
import com.example.xiwangnotepad.repository.data.YmdHmTimeModel

/**   
 * 包名称： com.example.xiwangnotepad.viewmodel
 * 类名称：BottomTimeViewModel
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-04 10:30
 *
 */
class BottomTimeViewModel: ViewModel (){
    private var _liveData = MutableLiveData<HmTimeModel>()
    val liveData: LiveData<HmTimeModel> get() = _liveData
    fun setTime(time:HmTimeModel){
        _liveData.value=time
    }
}
class BottomTimeViewModel2: ViewModel() {
    private var _liveData = MutableLiveData<YmdHmTimeModel>()
    val liveData: LiveData<YmdHmTimeModel> get() = _liveData
    fun setDate(time: YmdHmTimeModel){
        _liveData.value=time
    }
}