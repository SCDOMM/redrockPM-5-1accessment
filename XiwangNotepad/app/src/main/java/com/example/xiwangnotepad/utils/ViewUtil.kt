package com.example.xiwangnotepad.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast

/**   
 * 包名称： com.example.xiwangnotepad.util
 * 类名称：ViewUtil
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-01 20:01
 *
 */
infix fun EditText.wordMaxLimit(max: Int) {

    addTextChangedListener(object : TextWatcher {
        private var isEdit: Boolean=false
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (isEdit)return
            if (!p0.isNullOrEmpty()&&p0.toString().length > max) {
                isEdit=true
                setText(p0.subSequence(0, max))
                setSelection(max)
                isEdit=false
            }
        }
    })
}
fun showToast(context: Context,content: String){
    Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
}
