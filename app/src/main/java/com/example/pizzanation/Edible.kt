package com.example.pizzanation

public class Edible(name:String, price:Int,category:String, description:String) {
    public lateinit var category:String
    public lateinit var description:String
    public lateinit var name:String
    public var price:Int
    init{
        this.category=category
        this.name=name
        this.price=price
        this.description=description
    }
}