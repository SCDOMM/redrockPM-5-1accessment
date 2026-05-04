package com.example.xiwangnotepad.repository.api

import com.example.xiwangnotepad.repository.ResponseSingleData
import com.example.xiwangnotepad.repository.data.LoginDTO
import com.example.xiwangnotepad.repository.data.LoginModel
import com.example.xiwangnotepad.repository.data.RegisterDTO
import com.example.xiwangnotepad.repository.data.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**   
 * 包名称： com.example.xiwangnotepad.repository.api
 * 类名称：PersonalAPI
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-01 13:39
 *
 */

interface LoginAPI {
   @POST("/login")
   fun postData(@Body data: LoginModel): Call<ResponseSingleData<LoginDTO>>
}
interface RegisterAPI{
    @POST("/register")
    fun postData(@Body data: RegisterModel): Call<ResponseSingleData<RegisterDTO>>
}


