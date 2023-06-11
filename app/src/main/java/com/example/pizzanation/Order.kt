package com.example.pizzanation

class Order(order:ArrayList<orderItem>,totalAmount:Int,userId:Int,orderId:Int) {
    public lateinit var order:ArrayList<orderItem>
    public var totalAmount:Int
    public var userId:Int
    public var orderId:Int

    init{
        this.order = order
        this.totalAmount = totalAmount
        this.userId = userId
        this.orderId = orderId
    }
}