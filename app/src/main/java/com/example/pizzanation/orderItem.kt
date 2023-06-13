package com.example.pizzanation

import android.icu.number.IntegerWidth

class orderItem(itemName:String,quantity:Int,itemPrice:Int,netPrice:Int,crust:String) {
    public lateinit var itemName:String
    public var quantity:Int
    public var itemPrice:Int
    public var netPrice:Int
    public var crust:String
    public lateinit var imageURL:String
    init{
        this.itemName = itemName
        this.quantity = quantity
        this.itemPrice = itemPrice
        this.netPrice = netPrice
        this.crust = crust

    }
    public fun setNetPrice(){
        netPrice = itemPrice*quantity
    }
}