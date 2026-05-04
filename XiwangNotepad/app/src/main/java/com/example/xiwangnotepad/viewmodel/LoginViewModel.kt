package com.example.xiwangnotepad.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xiwangnotepad.repository.ResponseSingleData
import com.example.xiwangnotepad.repository.api.PersonalClient
import com.example.xiwangnotepad.repository.data.LoginDTO
import com.example.xiwangnotepad.repository.data.LoginModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**   
 * 包名称： com.example.xiwangnotepad.viewmodel
 * 类名称：LoginViewModel
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-01 22:11
 *
 */
class LoginViewModel : ViewModel() {
    private val _liveData = MutableLiveData<LoginState>()
    val liveData: LiveData<LoginState> get()=_liveData

    fun requestData(data: LoginModel) {
        if (data.account.isEmpty() || data.password.isEmpty()) {
            _liveData.value = LoginState.Failed("错误！账号/密码为空！")
            return
        }
        if (data.account.length < 6 || data.password.length < 6) {
            _liveData.value = LoginState.Failed("错误！账号/密码长度不够！")
            return
        }
        viewModelScope.launch {
            val response = PersonalClient.loginRequest(data)
            response.enqueue(object : Callback<ResponseSingleData<LoginDTO>> {
                override fun onResponse(
                    p0: Call<ResponseSingleData<LoginDTO>?>?,
                    p1: Response<ResponseSingleData<LoginDTO>?>?
                ) {
                    try {
                        if (p1 == null) {
                            _liveData.value = LoginState.Failed("错误！返回数据为空！")
                            return
                        }
                        if (!p1.isSuccessful) {
                            _liveData.value = LoginState.Failed("错误！返回未成功！")
                            return
                        }
                        val body = p1.body()
                        when (body?.status) {
                            "200" -> _liveData.value = LoginState.Success(body)
                            "400" -> _liveData.value = LoginState.Failed("登录失败！" + body.info)
                            null -> _liveData.value = LoginState.Failed("错误！解析为空！")
                            else -> _liveData.value = LoginState.Failed(body.status + body.info)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("登录错误！", p1.toString())
                        _liveData.value = LoginState.Failed("网络异常！" + e.message)
                    }

                }

                override fun onFailure(p0: Call<ResponseSingleData<LoginDTO>?>?, p1: Throwable?) {
                    p1?.printStackTrace()
                    Log.d("登录错误！", p1.toString())
                    _liveData.value = LoginState.Failed("请求错误！" + p1?.message)
                }
            })
        }
    }
}

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val data: ResponseSingleData<LoginDTO>?) : LoginState()
    data class Failed(val msg: String) : LoginState()
}

