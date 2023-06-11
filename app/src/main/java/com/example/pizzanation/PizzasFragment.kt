package com.example.pizzanation

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pizzanation.database.MyDBHandler
import com.example.pizzanation.databinding.FragmentPizzasBinding
import com.example.pizzanation.main.MainActivity
import com.example.pizzanation.pizza.PizzasViewModel
import com.example.pizzanation.pizza.PizzasViewModelFactory
import com.example.pizzanation.roomdb.PizzaDatabase
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PizzasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PizzasFragment(fragment:String) : Fragment(R.layout.fragment_pizzas) {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var pizzaref: DatabaseReference
    private lateinit var pizzasViewModel: PizzasViewModel
//    private lateinit var pizzaViewModelFactory: PizzaViewModelFactory
    private lateinit var edibles: ArrayList<Edible>
    private lateinit var myDBHandler: MyDBHandler
    var fragment = fragment

    lateinit var binding:FragmentPizzasBinding

    lateinit var tablayout:TabLayout
    lateinit var itemsRecyclerView: RecyclerView
    lateinit var adapter: itemsAdapter
    var contex: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //Toast.makeText(context,"onViewCreated .... after pizzaref",Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPizzasBinding.inflate(inflater,container,false)
        //val view1:View = LayoutInflater.from(context).inflate(R.layout.fragment_pizzas,container,false)
        val view = inflater.inflate(R.layout.fragment_pizzas, container, false)
        binding=FragmentPizzasBinding.bind(view)
        //val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setContentView(MainActivity::class.java,R.layout.fragment_pizzas)
        val pizzasViewModel:PizzasViewModel = ViewModelProvider(this,PizzasViewModelFactory())[PizzasViewModel::class.java]//= PizzasViewModel()//by viewModels()
        edibles = ArrayList<Edible>()
        val edibleObserver = Observer<ArrayList<Edible>>{ newList->
            edibles = newList
            setRecyclerViewAdapter(newList)
        }
        pizzasViewModel.edibles.observe(viewLifecycleOwner,edibleObserver)
        this.contex = context?.applicationContext
        myDBHandler = MyDBHandler(this@PizzasFragment.requireContext())
        var roomDatabase: RoomDatabase = Room.databaseBuilder(
            requireContext(),
            PizzaDatabase::class.java, "PizzaDatabase"
        ).build()
        //itemsRecyclerView = view.findViewById<RecyclerView>(R.id.items_recycler_view)
        //fetches data for edibles
        //listens in realtime to firebase realtime database  #rtdb


//        pizzaref = FirebaseDatabase.getInstance().getReference()
//        if(pizzaref == null)
//            Toast.makeText(context,"onViewCreated .... after pizzaref is null",Toast.LENGTH_SHORT).show()
//        Toast.makeText(context,"onViewCreated .... after pizzaref",Toast.LENGTH_SHORT).show()
//        pizzaref.addValueEventListener(object : ValueEventListener {
//            @NonNull
//            override fun onDataChange(@NonNull dataSnapshot : DataSnapshot ) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                //val value = dataSnapshot.getValue<String>()
//                //dataSnapshot.child("edibles")
//                //    .getValue(HashMap<String, HashMap<String, String>>().javaClass)
//                for (snapshot: DataSnapshot in dataSnapshot.child("edibles").children) {
//                    val edible: String = snapshot.key.toString()
//                    //Toast.makeText(context,"onViewCreated ..key.. "+snapshot.key.toString(),Toast.LENGTH_SHORT).show()
//                    val category:String = snapshot.child("category").getValue().toString()
//                    val description:String = snapshot.child("description").getValue().toString()
//                    val quant: String = snapshot.child("price").getValue().toString()
//                    var quantity:Int = 0
//                    for(i in 0..quant.length-1){
//                        val a=(quant[i]-'0')*(Math.pow(10.0, quant.length.toDouble() - 1 - i))
//                        quantity += a.toInt()
//                    }
//                    edibles.add(Edible(edible,quantity,category,description))
//                }
//                //setRecyclerViewAdapter(edibles)
//                //Toast.makeText(context,"adapter is all set"+edibles.get(0).description,Toast.LENGTH_SHORT).show()
//                //Log.d(TAG, "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                //Toast.makeText(context,"onViewCreated .... "+edibles.size,Toast.LENGTH_SHORT).show()
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })
        //setRecyclerViewAdapter(pizzasViewModel.pizzaModel.edibles)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PizzasFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PizzasFragment(fragment = "pizza").apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
    fun pizzaClick(view: View){
        if(this.fragment == "pizza")
            return
    }
    fun sidesClick(view: View){
        if(this.fragment == "sides")
            return
    }
    fun setRecyclerViewAdapter(edibles:ArrayList<Edible>){
        val itemList:ArrayList<Edible> = ArrayList<Edible>()
        for(item:Edible in edibles) {
            // sides / pizzas
            if(this.fragment.equals(item.category.toString()))
                itemList.add(item)
        }
        this.adapter = itemsAdapter(itemList, requireContext(),this.myDBHandler)
        binding.itemsRecyclerView.adapter = this.adapter
        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(context)
        //Toast.makeText(context,"onViewCreated .... 1232"+edibles.size,Toast.LENGTH_SHORT).show()
//        binding.dataDemo.text = itemList.get(0).description
    }
}