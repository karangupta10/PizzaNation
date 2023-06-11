package com.example.pizzanation.cart

import com.example.pizzanation.database.MyDBHandler
import com.example.pizzanation.orderItem
import com.google.firebase.storage.FirebaseStorage

class CartModel(cartViewModel:CartViewModel){
    lateinit var cartViewModel:CartViewModel
    lateinit var cartListItems:ArrayList<orderItem>
    lateinit var databaseHelper:MyDBHandler
    lateinit var storage:FirebaseStorage
    init{
        this.cartViewModel = cartViewModel
        databaseHelper = MyDBHandler(cartViewModel.app)
        val cartItemsList:ArrayList<orderItem> = databaseHelper.getAllItems()
        storage = FirebaseStorage.getInstance()
//        storage.
    }
}