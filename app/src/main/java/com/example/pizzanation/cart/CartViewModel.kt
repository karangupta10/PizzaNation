package com.example.pizzanation.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzanation.database.MyDBHandler
import com.example.pizzanation.orderItem

class CartViewModel(application: Application): AndroidViewModel(application) {
    lateinit var cartItemsList:MutableLiveData<ArrayList<orderItem>>
    var app:Application = application
    var cartModel:CartModel = CartModel(this)
    //var myDBHandler:MyDBHandler = MyDBHandler(getApplication<Application>().applicationContext)
}