package com.example.xiwangnotepad.repository.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**   
 * 包名称： com.example.xiwangnotepad.repository.data
 * 类名称：HomeCardModel
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-03 12:28
 *
 */

@Entity(tableName = "card_model")
data class CardModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    var sortOrder:Int=id,
    val name: String,
    var timeSamp:Long,
    val remark: String
)