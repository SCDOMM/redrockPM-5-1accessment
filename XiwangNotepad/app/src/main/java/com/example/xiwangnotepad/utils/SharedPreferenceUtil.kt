package com.example.xiwangnotepad.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**   
 * 包名称： com.example.xiwangnotepad.utils
 * 类名称：SharedPreferenceUtil
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-02 15:43
 *
 */
object SharedPreferenceUtil {
    fun getLoginESP(context: Context): SharedPreferences{
        val masterKey:MasterKey= MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,"LoginInformation",masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }
    fun initializationSP(context: Context){
        val editor=getLoginESP(context).edit()
        editor.putBoolean("LoginStatus",false)
        editor.putString("name","棍木")
        editor.putString("account","unknown")
        editor.apply()
    }
    fun loginDetection(context: Context):Boolean{
        val esp=getLoginESP(context)
        return esp.getBoolean("LoginStatus",false)
    }



}







