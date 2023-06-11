package com.example.pizzanation.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//entities contains the table names, CartItems is a table
@Database(entities = [CartItems::class], version = 1)
abstract class PizzaDatabase() :RoomDatabase(){
    abstract fun cartItemsDao():CartItemsDao

//    companion object{
//        @Volatile
//        var INSTANCE:PizzaDatabase? = null
//        fun getDatabase(context: Context):PizzaDatabase{
//            return INSTANCE ?:Synchronized(this){
//                val instance = Room.databaseBuilder(context,PizzaDatabase::class.java,"PizzaDatabase").build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}