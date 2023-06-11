package com.example.pizzanation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzanation.database.MyDBHandler
import com.example.pizzanation.main.MainActivity

public class cartItemsAdapter(val context: Context, val cartItemsList:ArrayList<orderItem>, val myDBHandler:MyDBHandler): RecyclerView.Adapter<cartItemsAdapter.MyViewHolder>() {

    var crust_price = HashMap<String,Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        crust_price.put("small",50)
        crust_price.put("New Hand Tossed",80)
        crust_price.put("100% Wheat Thin Crust",120)
        crust_price.put("Cheese Burst",130)
        crust_price.put("Fresh Pan Pizza",100)
        crust_price.put("Classic Hand Tossed",100)
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.item_price.text = cartItemsList.get(position).itemPrice.toString()
        holder.sub_total.text = cartItemsList.get(position).netPrice.toString()
        holder.quantity.text = cartItemsList.get(position).quantity.toString()
        holder.crust.text = cartItemsList.get(position).crust
        holder.item_name.text = cartItemsList.get(position).itemName
        if(cartItemsList.get(position).crust != "null"){
            holder.crust.visibility = View.VISIBLE
            holder.crust_charge.visibility = View.VISIBLE
            holder.crust.text = cartItemsList.get(0).crust
            holder.crust_charge.text = crust_price.get(cartItemsList.get(0).crust).toString()
        }
        holder.delete.setOnClickListener(View.OnClickListener {
            var item:orderItem = orderItem(cartItemsList.get(position).itemName,1,cartItemsList.get(position).itemPrice,cartItemsList.get(position).itemPrice,cartItemsList.get(position).crust)
            myDBHandler.deleteFromCart(item)
            val intent = Intent(context,MainActivity::class.java)
            startActivity(context,intent,null)
        })
    }

    override fun getItemCount(): Int {
        return cartItemsList.size
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val item_name:TextView = itemView.findViewById(R.id.item_name)
        val item_image: ImageView = itemView.findViewById(R.id.item_image)
        val item_price: TextView = itemView.findViewById(R.id.pizza_price)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val sub_total: TextView = itemView.findViewById(R.id.sub_total)
        val crust:TextView = itemView.findViewById(R.id.crust_name)
        val crust_charge:TextView = itemView.findViewById(R.id.crust_extra_charge)
        val delete:ImageButton = itemView.findViewById(R.id.delete_button)
    }
}
