package com.example.xiwangnotepad

import android.content.Intent
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import com.example.xiwangnotepad.databinding.ActivityMainBinding
import com.example.xiwangnotepad.utils.SharedPreferenceUtil
import com.example.xiwangnotepad.view.FragmentInterface
import com.example.xiwangnotepad.view.activity.LoginActivity
import com.example.xiwangnotepad.view.adapter.MainAdapter
import com.example.xiwangnotepad.view.fragment.CreateFragment
import com.example.xiwangnotepad.view.fragment.HomeFragment
import com.example.xiwangnotepad.view.fragment.PersonalFragment

class MainActivity : FragmentActivity() {
    private var fragments= ArrayList<FragmentInterface>()
    private var _binding: ActivityMainBinding?=null
    private val binding: ActivityMainBinding get() =_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvent()
        initBNV()
    }

    fun initEvent() {
        val esp= SharedPreferenceUtil.getLoginESP(this)

        fragments.add { HomeFragment() }
        fragments.add { CreateFragment() }
        fragments.add { PersonalFragment() }
        val adapter = MainAdapter(fragments, this)
        binding.mainVP2Default.adapter = adapter

        if(!esp.getBoolean("LoginStatus",false)){
            startActivity(Intent(this, LoginActivity::class.java))
            SharedPreferenceUtil.initializationSP(this)
            return
        }
    }
    fun initBNV(){
        binding.mainBNVDefault.menu[0].isChecked = true
        binding.mainBNVDefault.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item1_Bnv -> {
                    binding.mainVP2Default.setCurrentItem(0,true)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_item2_Bnv->{
                    binding.mainVP2Default.setCurrentItem(1,true)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_item3_Bnv->{
                    binding.mainVP2Default.setCurrentItem(2,true)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
    }

}