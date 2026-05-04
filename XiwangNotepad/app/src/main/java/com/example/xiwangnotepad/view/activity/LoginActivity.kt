package com.example.xiwangnotepad.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.xiwangnotepad.R
import com.example.xiwangnotepad.databinding.ActivityLoginBinding
import com.example.xiwangnotepad.databinding.ActivityMainBinding
import com.example.xiwangnotepad.repository.data.LoginModel
import com.example.xiwangnotepad.utils.SharedPreferenceUtil
import com.example.xiwangnotepad.utils.showToast
import com.example.xiwangnotepad.utils.wordMaxLimit
import com.example.xiwangnotepad.viewmodel.LoginState
import com.example.xiwangnotepad.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding?=null
    private val binding get(): ActivityLoginBinding=_binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        initEvent()
        observeLogin()
    }

    fun initEvent() {
        binding.loginIvSwitch.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginEtvAccount.wordMaxLimit(12)
        binding.loginEtvPassword.wordMaxLimit(12)
        binding.loginBtnConfirm.setOnClickListener {
            viewModel.requestData(
                LoginModel(
                    binding.loginEtvAccount.text.toString(),
                    binding.loginEtvPassword.text.toString()
                )
            )
        }
    }
    fun observeLogin() {
        viewModel.liveData.observe(this, Observer{value->
            when(value){
                is LoginState.Loading->{
                    showToast(this,"正在登录...")
                }
                is LoginState.Success->{
                    showToast(this,"登录成功！"+value.data?.info)
                    val editor= SharedPreferenceUtil.getLoginESP(this).edit()
                    editor.putBoolean("LoginStatus",true).apply()
                    finish()
                }
                is LoginState.Failed->{
                    showToast(this,"登录失败！"+value.msg)
                }
            }
        })

    }


}