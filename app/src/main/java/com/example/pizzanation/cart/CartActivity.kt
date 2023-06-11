package com.example.pizzanation.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzanation.Order
import com.example.pizzanation.R
import com.example.pizzanation.cartItemsAdapter
import com.example.pizzanation.database.MyDBHandler
import com.example.pizzanation.databinding.ActivityCartBinding
import com.example.pizzanation.main.MainActivity
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [CartActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartActivity : AppCompatActivity() {

    lateinit var cartBinding:ActivityCartBinding
    lateinit var cartViewModel:CartViewModel
    lateinit var pizzaButton: ImageButton
    lateinit var cartButton: ImageButton
    lateinit var myDBHandler:MyDBHandler
    lateinit var pizzaref: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        cartViewModel = ViewModelProvider(this,CartViewModelFactory(application))[CartViewModel::class.java]
        pizzaButton = cartBinding.pizzaButton
        cartButton = cartBinding.cartButton
        myDBHandler = MyDBHandler(this)
        val cartItems = myDBHandler.getAllItems()
        val adapter:cartItemsAdapter = cartItemsAdapter(this,cartItems,myDBHandler)
        cartBinding.totalInRs.text = "Rs. " + totalPrice()
        cartBinding.recyclerView.adapter = adapter
        cartBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
    fun pizzaMenu(view: View){
        pizzaButton.setBackgroundResource(R.drawable.icon_clicked)
        cartButton.setBackgroundResource(R.drawable.icon_back)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun placeOrder(view: View){
        val listItems = myDBHandler.getAllItems()
        val order = Order(listItems,myDBHandler.totalCost(),1,1)

        Toast.makeText(this,"Order Placed !!",Toast.LENGTH_LONG).show()
    }
    fun totalPrice():Int{
        var totalCost = myDBHandler.totalCost()
        var allItems = myDBHandler.getAllItems()
        var crust_total:Int = 0
        var crust_price = HashMap<String,Int>()
        crust_price.put("small",50)
        crust_price.put("New Hand Tossed",80)
        crust_price.put("100% Wheat Thin Crust",120)
        crust_price.put("Cheese Burst",130)
        crust_price.put("Fresh Pan Pizza",100)
        crust_price.put("Classic Hand Tossed",100)
        for(item in allItems){
            if(item.crust != "null")
                crust_total += crust_price.get(item.crust)?.times(item.quantity)!!
        }
        return totalCost+crust_total
    }
}