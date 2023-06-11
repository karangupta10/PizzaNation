package com.example.pizzanation.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pizzanation.orderItem

@Dao
interface CartItemsDao {
//    @Query("SELECT * from CartItems")
//    fun getAllItems():List<orderItem>
//
//    @Query("SELECT COUNT(*) FROM CartItems from WHERE ITEM_NAME = :First AND CRUST = :Second")
//    fun countSimilarItems(First: String, Second:String):Int
//
//    @Query("UPDATE cart_items SET repetitions = repetitions+1 WHERE item_name = :First and crust = :Second")
//    fun incrementQuant(First:String,Second:String)
//
//    @Query("UPDATE cart_items SET net_price = repetitions*item_price WHERE item_name = :First and crust = :Second")
//    fun adjustNetPrice(First:String, Second:String)
//
//    @Query("UPDATE cart_items SET repetitions = repetitions-1 WHERE item_name = :First and crust = :Second")
//    fun decrementQuant(First:String,Second:String)
//
//    @Insert
//    fun insertAll(vararg item: orderItem)
//
//    @Delete
//    fun delete(item: orderItem)
}