package com.example.xiwangnotepad.view.activity

import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.xiwangnotepad.databinding.ActivityHomeDetailsBinding
import com.example.xiwangnotepad.repository.data.CardModel
import com.example.xiwangnotepad.utils.toYmdHm
import com.example.xiwangnotepad.view.fragment.BottomTimeFragment
import com.example.xiwangnotepad.viewmodel.BottomTimeViewModel2
import com.example.xiwangnotepad.viewmodel.HomeDetailViewModel

class HomeDetailActivity : AppCompatActivity() {
    private var _binding: ActivityHomeDetailsBinding? = null
    private val binding: ActivityHomeDetailsBinding get() = _binding!!
    private lateinit var viewModel: HomeDetailViewModel
    private lateinit var viewModel2: BottomTimeViewModel2
    private var timeSamp: Long = 0
    private lateinit var cardModel: CardModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeDetailsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeDetailViewModel::class.java]
        viewModel2 = ViewModelProvider(this)[BottomTimeViewModel2::class.java]
        setContentView(binding.root)

        viewModel.initData(intent.getIntExtra("id",0))
        viewModel.liveData.observe(this) {_->
            initEvent()
        }
    }

    fun initEvent() {
        cardModel = viewModel.liveData.value!!
        timeSamp = cardModel.timeSamp

        binding.homeDetailTbTop.setNavigationOnClickListener { finish() }
        binding.homeDetailEtvName.setText(cardModel.name)
        binding.homeDetailEtvTime.text = cardModel.timeSamp.toYmdHm()
        binding.homeDetailEtvTime.setOnClickListener {
            initTime()
        }
        binding.homeDetailTvSubmit.setOnClickListener {
            initSave()
        }
    }

    fun initTime() {
        val bottomSheet = BottomTimeFragment()
        bottomSheet.show(supportFragmentManager, "TimeDialog")
        viewModel2.liveData.observe(this) { time ->
            val calendar = Calendar.getInstance().apply {
                set(time.year, time.month, time.day, time.hour, time.minute, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }
            val timeData = calendar.timeInMillis
            timeSamp = timeData
            binding.homeDetailEtvTime.text = timeData.toYmdHm()
        }
    }

    fun initSave() {
        cardModel = CardModel(
            intent.getIntExtra("id", 0),
            cardModel.sortOrder,
            binding.homeDetailEtvName.text.toString(),
            timeSamp,
            ""
        )
        viewModel.updateCard(cardModel)
        setResult(RESULT_OK)
        finish()
    }
}