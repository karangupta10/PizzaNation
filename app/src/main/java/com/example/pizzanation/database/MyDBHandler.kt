package com.example.pizzanation.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pizzanation.Order
import com.example.pizzanation.orderItem

class MyDBHandler(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "pizza_database"
        private const val DATABASE_VERSION = 1
        private const val ID = "id"
        private const val CART_ITEMS = "cart_items"
        private const val ITEM_NAME = "item_name"
        private const val REPETITIONS = "repetitions"
        private const val ITEM_PRICE = "item_price"
        private const val NET_PRICE = "net_price"
        private const val CRUST = "crust"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableOrders = ( " CREATE TABLE " +  CART_ITEMS   + " ( " +ID + " INTEGER PRIMARY KEY, "+ ITEM_NAME + " TEXT, " + REPETITIONS + " INT, " + ITEM_PRICE + " INT, "+ NET_PRICE + " INT, " + CRUST + " TEXT )" )
        db?.execSQL(createTableOrders)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $CART_ITEMS")
        onCreate(db)
    }
    public fun addToCart(item: orderItem): Boolean {
        val database:SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = ContentValues()
        contentValues.put("item_name",item.itemName)
        contentValues.put("repetitions",item.quantity.toString())
        contentValues.put("item_price",item.itemPrice.toString())
        contentValues.put("net_price",item.netPrice.toString())
        contentValues.put("crust",item.crust)
        val success = database.insert("cart_items",null,contentValues)
        return true
    }
    public fun deleteFromCart(item: orderItem):Boolean {
        val database:SQLiteDatabase = this.writableDatabase
        database.delete("cart_items","item_name = ? AND crust = ?", arrayOf(item.itemName,item.crust))
        return true
    }
    public fun deleteAll(){
        val database:SQLiteDatabase = this.writableDatabase
        database.delete("cart_items","",null)
    }
    public fun getAllItems():ArrayList<orderItem>{
        val database:SQLiteDatabase = this.writableDatabase
        val itemsList:ArrayList<orderItem> = ArrayList<orderItem>()
        val cursor:Cursor = database.rawQuery("SELECT * FROM cart_items",null)
        if(cursor.moveToFirst()){
            do{
                val item_name =  cursor.getString(1).toString()
                val repetitions = cursor.getInt(2)
                val item_price = cursor.getInt(3)
                val net_price = cursor.getInt(4)
                val crust_select = cursor.getString(5).toString()
                itemsList.add(orderItem(item_name,repetitions,item_price,net_price,crust_select))
            }while(cursor.moveToNext())
        }
        return itemsList
    }
    public fun countSameItems(item:orderItem):Int{
        var count:Int = 0
        val name = item.itemName
        val crust = item.crust
        val database:SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("SELECT repetitions FROM cart_items WHERE item_name = ? AND crust = ?",arrayOf(name,crust))
        //val result = database.execSQL("SELECT COUNT(item_name) as count_col FROM cart_items where item_name = $name and crust = $crust")

        if(cursor.moveToFirst()){
            do{
                val ct:String = cursor.getString(0)
                var quantity:Int = 0
                    for(i in 0..ct.length-1){
                        val a=(ct[i]-'0')*(Math.pow(10.0, ct.length.toDouble() - 1 - i))
                        quantity += a.toInt()
                    }
                    count = quantity
                break
            }while(cursor.moveToNext())
        }
        return count
    }
    public fun countSimilarTypes(itemName:String):Int{
        var count:Int = 0
        val name = itemName
        val database:SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("SELECT COUNT(*) FROM cart_items WHERE item_name = ?",arrayOf(name))
        //val result = database.execSQL("SELECT COUNT(item_name) as count_col FROM cart_items where item_name = $name and crust = $crust")

        if(cursor.moveToFirst()){
            do{
                val ct:String = cursor.getString(0)
                var quantity:Int = 0
                for(i in 0..ct.length-1){
                    val a=(ct[i]-'0')*(Math.pow(10.0, ct.length.toDouble() - 1 - i))
                    quantity += a.toInt()
                }
                count = quantity
                break
            }while(cursor.moveToNext())
        }
        return count
    }
    public fun countSimilarItems(itemName:String):Int{
        var count:Int = 0
        val name = itemName
        val database:SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("SELECT repetitions FROM cart_items WHERE item_name = ? ",arrayOf(name))
        //val result = database.execSQL("SELECT COUNT(item_name) as count_col FROM cart_items where item_name = $name and crust = $crust")
        if(cursor.moveToFirst()){
            do{
                val ct:String = cursor.getString(0)
                var quantity:Int = 0
                for(i in 0..ct.length-1){
                    val a=(ct[i]-'0')*(Math.pow(10.0, ct.length.toDouble() - 1 - i))
                    quantity += a.toInt()
                }
                count += quantity
            }while(cursor.moveToNext())
        }
        return count
    }
    public fun increaseQuant(item:orderItem):Int{
        val database:SQLiteDatabase = this.writableDatabase
        val name = item.itemName
        val crust = item.crust
        database.execSQL("UPDATE cart_items SET repetitions = repetitions+1 WHERE item_name = ? and crust = ?",
            arrayOf(name,crust)
        )
        database.execSQL("UPDATE cart_items SET net_price = repetitions*item_price WHERE item_name = ? and crust = ?", arrayOf(name,crust))
        return 1;
    }
    public fun reduceQuant(item:orderItem):Int{
        val database:SQLiteDatabase = this.writableDatabase
        val name = item.itemName
        val crust = item.crust
        database.execSQL("UPDATE cart_items SET repetitions = repetitions-1 WHERE item_name = ? and crust = ?",
            arrayOf(name,crust)
        )
        database.execSQL("UPDATE cart_items SET net_price = repetitions*item_price WHERE item_name = ? and crust = ?", arrayOf(name,crust))
        return 1
    }
    public fun totalCost():Int{
        var count:Int=0
        val database:SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("SELECT repetitions*item_price FROM cart_items" ,null)
        if(cursor.moveToFirst()){
            do{
                val ct:String = cursor.getString(0)
                var quantity:Int = 0
                for(i in 0..ct.length-1){
                    val a=(ct[i]-'0')*(Math.pow(10.0, ct.length.toDouble() - 1 - i))
                    quantity += a.toInt()
                }
                count += quantity
            }while(cursor.moveToNext())
        }
        return count
    }
}