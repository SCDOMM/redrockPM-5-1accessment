package com.example.xiwangnotepad.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.xiwangnotepad.R
import com.example.xiwangnotepad.databinding.ActivityMainBinding
import com.example.xiwangnotepad.databinding.ActivityRegisterBinding
import com.example.xiwangnotepad.repository.data.RegisterModel
import com.example.xiwangnotepad.utils.showToast
import com.example.xiwangnotepad.utils.wordMaxLimit
import com.example.xiwangnotepad.viewmodel.RegisterState
import com.example.xiwangnotepad.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding?=null
    private val binding: ActivityRegisterBinding get() =_binding!!
    lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[RegisterViewModel::class.java]
        initEvent()
        registerObserver()
    }
    fun initEvent(){
        binding.registerIvSwitch.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.registerBtnConfirm.setOnClickListener{
            binding.registerEtvUsername.wordMaxLimit(8)
            binding.registerEtvAccount.wordMaxLimit(12)
            binding.registerEtvPassword.wordMaxLimit(12)
            binding.registerEtvRepassword.wordMaxLimit(12)
            if (!binding.registerEtvPassword.text.equals(binding.registerEtvRepassword)){
                showToast(this,"错误！两次输入密码不一致！")
                return@setOnClickListener
            }
            viewModel.requestData(RegisterModel(binding.registerEtvUsername.text.toString(),binding.registerEtvAccount.text.toString(),binding.registerEtvPassword.text.toString()))
        }
    }
    fun registerObserver(){
        viewModel.liveData.observe(this, Observer{ value->
            when(value){
                is RegisterState.Loading->{
                    showToast(this,"正在注册...")
                }
                is RegisterState.Success->{
                    showToast(this,"注册成功！"+ value.data.info)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                is RegisterState.Failed->{
                    showToast(this,"注册失败！"+value.msg)
                }
            }
        })


    }

}