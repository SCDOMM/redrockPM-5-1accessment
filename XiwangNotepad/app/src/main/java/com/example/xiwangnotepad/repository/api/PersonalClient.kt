package com.example.xiwangnotepad.repository.api

import com.example.xiwangnotepad.Config
import com.example.xiwangnotepad.repository.ResponseSingleData
import com.example.xiwangnotepad.repository.data.LoginDTO
import com.example.xiwangnotepad.repository.data.LoginModel
import com.example.xiwangnotepad.repository.data.RegisterDTO
import com.example.xiwangnotepad.repository.data.RegisterModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 包名称： com.example.xiwangnotepad.repository.client
 * 类名称：PersonalClient
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-01 13:46
 *
 */
object PersonalClient{
    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(Config.BASE_URI+Config.BASE_IMPORT).addConverterFactory(GsonConverterFactory.create()).build()
    }
    val loginService: LoginAPI by lazy {
        retrofit.create(LoginAPI::class.java)
    }
    val registerService: RegisterAPI by lazy {
        retrofit.create(RegisterAPI::class.java)
    }
    fun loginRequest(data: LoginModel): Call<ResponseSingleData<LoginDTO>> {
        return loginService.postData(data)
    }
    fun registerRequest(data: RegisterModel) :Call<ResponseSingleData<RegisterDTO>>{
        return registerService.postData(data)
    }

}