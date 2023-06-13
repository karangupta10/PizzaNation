 package com.example.pizzanation.pizza

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzanation.Edible
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PizzasViewModel(): ViewModel() {
    lateinit var data:String
    var edibles: MutableLiveData<ArrayList<Edible>> = MutableLiveData<ArrayList<Edible>>()
    var ediblePics:MutableLiveData<HashMap<String,String>> = MutableLiveData<HashMap<String,String>>()
    var pizzaModel: PizzaModel = PizzaModel(edibles,this)
}

