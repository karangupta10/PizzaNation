package com.example.pizzanation.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItems(
    @PrimaryKey(autoGenerate = true) var ID: Int =0,
    @ColumnInfo(name = "item_name") val itemName:String,
    @ColumnInfo(name = "repetitions") val repetitions:Int,
    @ColumnInfo(name = "item_price") val itemPrice:Int,
    @ColumnInfo(name = "net_price") val netPrice:Int,
    @ColumnInfo(name = "crust") val crust:String
)
