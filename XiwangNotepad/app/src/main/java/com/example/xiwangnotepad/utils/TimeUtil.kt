package com.example.xiwangnotepad.utils

import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.xiwangnotepad.repository.data.HmTimeModel
import com.example.xiwangnotepad.repository.data.YmdHmTimeModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**   
 * 包名称： com.example.xiwangnotepad.utils
 * 类名称：TimeUitl
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-03 19:33
 *
 */

fun Long.toYmdHm(): String{
    val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault())
    return sdf.format(this)
}
fun TimePicker.getTime(): HmTimeModel{
    Log.d("time is",this.hour.toString())
    return HmTimeModel(this.hour,this.minute)
}
fun DatePicker.getTime(): YmdHmTimeModel{
    return YmdHmTimeModel(this.year,this.month,this.dayOfMonth,0,0)
}
//fun getTimeSamp(timePicker: TimePicker,datePicker: DatePicker):Long{
//    val year=datePicker.year
//    val month=datePicker.month
//    val day=datePicker.dayOfMonth
//    val hour=timePicker.hour
//    val minute=timePicker.minute
//    val calendar= Calendar.getInstance().apply {
//        set(year,month,day,hour,minute,0)
//        set(Calendar.MILLISECOND, 0)
//    }
//    return calendar.timeInMillis
//}
