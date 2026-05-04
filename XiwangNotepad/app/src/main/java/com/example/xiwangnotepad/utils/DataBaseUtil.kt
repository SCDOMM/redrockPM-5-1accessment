package com.example.xiwangnotepad.utils

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.xiwangnotepad.repository.data.CardModel

/**   
 * 包名称： com.example.xiwangnotepad.utils
 * 类名称：DataBaseUtil
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-03 21:07
 *
 */
@Dao
interface CardDao {
    @Insert
    suspend fun insertCard(data: CardModel)

    @Update
    suspend fun updateCard(data: CardModel)

    @Delete
    suspend fun deleteCard(data: CardModel)
    @Query("DELETE FROM card_model")
    suspend fun deleteAll()
    @Query("SELECT * FROM card_model ORDER BY sortOrder ASC,id ASC")
    suspend fun getAllCards(): List<CardModel>
    @Query("SELECT * FROM card_model WHERE id = :id")
    suspend fun selectCardByID(id:Int): CardModel
    @Query("UPDATE card_model SET sortOrder = :newOrder WHERE id = :cardId")
    suspend fun updateSortOrder(cardId: Int, newOrder: Int)
}
@Database(entities = [CardModel::class], version = 2)
abstract class CardDataBase: RoomDatabase(){
    abstract fun cardDao(): CardDao
}
object DataBaseClient{
    @Volatile
    private var INSTANCE: CardDataBase?=null
    fun getDataBase(context: Context): CardDataBase{
        return INSTANCE?:synchronized(this){
            val instance=Room.databaseBuilder(context.applicationContext, CardDataBase::class.java,"card_model").build()
            INSTANCE=instance
            instance
        }
    }
}