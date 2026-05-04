package com.example.xiwangnotepad.repository

/**   
 * 包名称： com.example.xiwangnotepad.repository
 * 类名称：ResponseSingleData
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-01 13:34
 *
 */
data class ResponseSingleData<T>(val status: String,val info:String,val data:T)
data class ResponseArrayData<T>(val status: String,val info: String,val dataArray: ArrayList<T>)