package com.example.pizzanation.pizza

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pizzanation.Edible
import com.google.firebase.database.*
import kotlin.coroutines.coroutineContext

class PizzaModel(edibles: MutableLiveData<ArrayList<Edible>>,pizzaViewModel: PizzasViewModel) {
    public lateinit var pizzaref: DatabaseReference
    public lateinit var edibles: ArrayList<Edible>
    public lateinit var ediblePics: HashMap<String,String>
    public lateinit var pizzaViewModel: PizzasViewModel

    init{
        this.edibles = ArrayList<Edible>()
        this@PizzaModel.pizzaViewModel = pizzaViewModel
        pizzaref=FirebaseDatabase.getInstance().getReference()
        pizzaref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                this@PizzaModel.edibles = ArrayList<Edible>()
                this@PizzaModel.ediblePics = HashMap<String,String>()
                for (snapshot: DataSnapshot in dataSnapshot.child("edibles").children) {
                    val edible: String = snapshot.key.toString()
                    val description: String =
                        snapshot.child("description").getValue().toString()
                    val category =
                        snapshot.child("category").getValue().toString()
                    val quant: String = snapshot.child("price").getValue().toString()
                    var quantity:Int = 0
                    for(i in 0..quant.length-1){
                        val a=(quant[i]-'0')*(Math.pow(10.0, quant.length.toDouble() - 1 - i))
                        quantity += a.toInt()
                    }
                    this@PizzaModel.edibles.add(Edible(edible, quantity, category, description))
                }
                this@PizzaModel.pizzaViewModel.edibles.value = this@PizzaModel.edibles
                //setEdibles()
                for(snapshot: DataSnapshot in dataSnapshot.child("pictures").children){
                    ediblePics.put(snapshot.key.toString(),snapshot.value.toString())
                }
                this@PizzaModel.pizzaViewModel.ediblePics.value = this@PizzaModel.ediblePics

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
    public fun setListener(){

    }
    @JvmName("getEdibles1")
    public fun getEdibles(): ArrayList<Edible> {
        return this.edibles
    }
    public fun setEdibles(){
        this.edibles = pizzaViewModel.edibles.value!!
    }
    public fun setEdiblePics(){
        this.ediblePics = pizzaViewModel.ediblePics.value!!
    }
}