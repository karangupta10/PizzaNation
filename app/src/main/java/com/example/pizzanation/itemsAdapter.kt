package com.example.pizzanation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.transition.Visibility
import com.example.pizzanation.cart.CartActivity
import com.example.pizzanation.database.MyDBHandler
import com.example.pizzanation.roomdb.PizzaDatabase
import com.google.firebase.auth.ktx.oAuthProvider
import com.squareup.picasso.Picasso
import java.lang.Math.pow

public class itemsAdapter(val itemsList:ArrayList<Edible>, val picsList:HashMap<String,String>, val context: Context, myDBHandler: MyDBHandler): RecyclerView.Adapter<itemsAdapter.MyViewHolder>() {
    //    public var roomDatabase:RoomDatabase = Room.databaseBuilder(
//        context,
//        PizzaDatabase::class.java, "database-name"
//    ).build()
    public var roomDatabase:RoomDatabase = Room.databaseBuilder(context,PizzaDatabase::class.java,"PizzaDatabase").build()
    public var myDBHandler:MyDBHandler = myDBHandler
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.pizza_layout,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.description.text = itemsList.get(position).description
        holder.title.text = itemsList.get(position).name
        holder.item_price.text = "Rs." + itemsList.get(position).price.toString()
        //load image from URL using Picasso
        Picasso.get().load(picsList[itemsList.get(position).name]).into(holder.edibleImage)
        var adapter:ArrayAdapter<CharSequence>? = null
        if(context != null)
            adapter  = ArrayAdapter.createFromResource(context, R.array.crust_select, android.R.layout.simple_spinner_item);
        holder.crust_select.adapter = adapter
        holder.quantity.text = myDBHandler.countSimilarItems(itemsList.get(position).name).toString()
        if(myDBHandler.countSimilarItems(itemsList.get(position).name) > 0){
            holder.add_to_cart.visibility = View.INVISIBLE
            holder.adjust_quantity.visibility = View.VISIBLE
        }
        holder.add_to_cart.setOnClickListener(View.OnClickListener {
            if(holder.add_to_cart.isVisible) {
                holder.add_to_cart.visibility = View.GONE
                holder.adjust_quantity.visibility = View.VISIBLE
                holder.quantity.text = "1"

                var item:orderItem
                if(itemsList.get(position).category == "pizza")
                    item = orderItem(itemsList.get(position).name,1,itemsList.get(position).price,itemsList.get(position).price,holder.crust_select.selectedItem.toString())
                else item = orderItem(itemsList.get(position).name,1,itemsList.get(position).price,itemsList.get(position).price,"null")
                if(myDBHandler.countSameItems(item)>0){
                    myDBHandler.increaseQuant(item)
                }
                else myDBHandler.addToCart(item)

                //if(){
                    //this.roomDatabase.openHelper.writableDatabase.
                //}
                //myDBHandler.countSimilarItems(item)
                //Toast.makeText(context,"similar items to the one added in cart ... "+myDBHandler.countSimilarItems(item),Toast.LENGTH_SHORT)
            }
        })

        holder.plus.setOnClickListener(View.OnClickListener {
            val quant:String = holder.quantity.text.toString()
            var quantity:Int = 0
            for(i in 0..quant.length-1){
                val a=(quant[i]-'0')*(pow(10.0,quant.length.toDouble()-1 - i))
                quantity += a.toInt()
            }
            quantity+=1
            holder.quantity.text = quantity.toString()
            var item:orderItem
            if(itemsList.get(position).category == "pizza")
                item = orderItem(itemsList.get(position).name,1,itemsList.get(position).price,itemsList.get(position).price,holder.crust_select.selectedItem.toString())
            else item = orderItem(itemsList.get(position).name,1,itemsList.get(position).price,itemsList.get(position).price,"null")
            if(myDBHandler.countSameItems(item)>0)
                myDBHandler.increaseQuant(item)
            else {
                myDBHandler.addToCart(item)
                holder.quantity.text = myDBHandler.countSimilarItems(item.itemName).toString()
            }
        })

        holder.minus.setOnClickListener(View.OnClickListener {
            var item:orderItem
            if(itemsList.get(position).category == "pizza")
                item = orderItem(itemsList.get(position).name,1,itemsList.get(position).price,itemsList.get(position).price,holder.crust_select.selectedItem.toString())
            else item = orderItem(itemsList.get(position).name,1,itemsList.get(position).price,itemsList.get(position).price,"null")
            if(myDBHandler.countSimilarTypes(item.itemName)>1){
                Toast.makeText(context,"Multiple customizations have been selected for this item, GO TO CART TO REMOVE ITEM",Toast.LENGTH_SHORT)
                val intent: Intent = Intent(context,CartActivity::class.java)
                startActivity(context,intent,null)
                return@OnClickListener
            }
            val quant:String = holder.quantity.text.toString()
            var quantity:Int = 0
            for(i in 0..quant.length-1){
                val a=(quant[i]-'0')*(pow(10.0,quant.length.toDouble()-1 - i))
                quantity += a.toInt()
            }
            if(quantity == 1){
                holder.add_to_cart.visibility = View.VISIBLE
                holder.adjust_quantity.visibility = View.GONE
                holder.quantity.text = "0"
            }
            else{ quantity -= 1
                holder.quantity.text = quantity.toString()
            }
            myDBHandler.reduceQuant(item)
        })
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //val title: TextView = itemView.findViewById<TextView>(R.id.title)
        //val content: FragmentContainerView = itemView.findViewById<FragmentContainerView>(R.id.)
        //val body:TextView = content.findViewById<TextView>(R.id.body)

        val title:TextView = itemView.findViewById<TextView>(R.id.title)
        val description:TextView = itemView.findViewById<TextView>(R.id.description)
        val edibleImage:ImageView = itemView.findViewById<ImageView>(R.id.edible_image)

        val adjust_quantity: LinearLayout = itemView.findViewById<LinearLayout>(R.id.adjust_quantity)
        val item_price:TextView = itemView.findViewById<TextView>(R.id.item_price)
        val plus: ImageButton = itemView.findViewById<ImageButton>(R.id.plus)
        val minus: ImageButton = itemView.findViewById<ImageButton>(R.id.minus)
        val quantity : TextView = itemView.findViewById(R.id.quantity)
        val add_to_cart: Button = itemView.findViewById<Button>(R.id.add_to_cart)
        val crust_select: Spinner = itemView.findViewById<Spinner>(R.id.crust_select)
    }
}