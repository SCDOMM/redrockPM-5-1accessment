package com.example.xiwangnotepad.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xiwangnotepad.repository.ResponseSingleData
import com.example.xiwangnotepad.repository.api.PersonalClient
import com.example.xiwangnotepad.repository.data.RegisterDTO
import com.example.xiwangnotepad.repository.data.RegisterModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**   
 * 包名称： com.example.xiwangnotepad.viewmodel
 * 类名称：RegisterViewModel
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-01 22:11
 *
 */
class RegisterViewModel: ViewModel() {
    private val _liveData= MutableLiveData<RegisterState>()
    val liveData: LiveData<RegisterState>get()=_liveData
    fun requestData(data:RegisterModel){
        if (data.userName.isEmpty()||data.account.isEmpty() || data.password.isEmpty()) {
            _liveData.value = RegisterState.Failed("错误！有信息为空！")
            return
        }
        if (data.userName.length<2||data.account.length < 6 || data.password.length < 6) {
            _liveData.value = RegisterState.Failed("错误！有信息长度不够！")
            return
        }
        viewModelScope.launch {
            val response = PersonalClient.registerRequest(data)
            response.enqueue(object : Callback<ResponseSingleData<RegisterDTO>> {
                override fun onResponse(
                    p0: Call<ResponseSingleData<RegisterDTO>?>?,
                    p1: Response<ResponseSingleData<RegisterDTO>?>?
                ) {
                    try {
                        if (p1 == null) {
                            _liveData.value = RegisterState.Failed("错误！返回数据为空！")
                            return
                        }
                        if (!p1.isSuccessful) {
                            _liveData.value = RegisterState.Failed("错误！返回未成功！")
                            return
                        }
                        val body = p1.body()
                        when (body?.status) {
                            "200" -> _liveData.value = RegisterState.Success(body)
                            "400" -> _liveData.value = RegisterState.Failed("注册失败！" + body.info)
                            null -> _liveData.value = RegisterState.Failed("错误！解析为空！")
                            else -> _liveData.value = RegisterState.Failed(body.status + body.info)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("登录错误！", p1.toString())
                        _liveData.value = RegisterState.Failed("网络异常！" + e.message)
                    }
                }

                override fun onFailure(
                    p0: Call<ResponseSingleData<RegisterDTO>?>?,
                    p1: Throwable?
                ) {
                    p1?.printStackTrace()
                    Log.d("注册错误！", p1.toString())
                    _liveData.value = RegisterState.Failed("请求错误！" + p1?.message)
                }
            })
        }
    }
}
sealed class RegisterState{
    object Loading: RegisterState()
    data class Success(val data: ResponseSingleData<RegisterDTO>): RegisterState()
    data class Failed(val msg:String):RegisterState()
}
