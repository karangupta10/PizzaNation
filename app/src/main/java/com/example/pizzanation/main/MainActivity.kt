package com.example.pizzanation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pizzanation.*
import com.example.pizzanation.R
import com.example.pizzanation.cart.CartActivity
import com.example.pizzanation.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding       //View Binding
    lateinit var dataBindingUtil: DataBindingUtil
    lateinit var textView: TextView
    lateinit var menuBar: LinearLayout
    lateinit var pizzaButton:ImageButton
    lateinit var cartButton:ImageButton
    lateinit var mainViewModel: MainViewModel
    lateinit var viewpager2: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var fragmentAdapter:MyFragmentAdapter
    lateinit var adapter:itemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(this,MainViewModelFactory())[MainViewModel::class.java]
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var sharedPreferences = getPreferences(MODE_PRIVATE)
        var bar = supportActionBar
        mainViewModel.demo_data = "hello world"
        //this.savedInstanceState = savedInstanceState
//        if (savedInstanceState == null) {                           //code for adding fragment by code inside FragmentContainerView
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<PizzasFragment>(R.id.fragment_main);
//            }
//        }
        fragmentAdapter = MyFragmentAdapter(supportFragmentManager,lifecycle)
        viewpager2 = binding.viewpager2
        viewpager2.adapter = fragmentAdapter
        tabLayout = binding.tabsLayout
        cartButton = binding.cartButton
        pizzaButton = binding.pizzaButton
        //val tab1: TabLayout.Tab = TabLayout.Tab()
//        tab1.text = "Pizza"
        //tab1.select()
        //val tab2: TabLayout.Tab = TabLayout.Tab()
//        tab1.text = "Side"
        //tabLayout.addTab(TabLayout.Tab(),0)
        //tabLayout.addTab(TabLayout.Tab(),1)
//        tabLayout.getTabAt(0)?.text ="Pizza"
//        tabLayout.getTabAt(1)?.text ="Side"
//        tabLayout.addTab(tab1,0)
//        tabLayout.addTab(tab2,1)
        binding.tabsLayout.getTabAt(0)?.text = "Pizza"
        binding.tabsLayout.getTabAt(1)?.text = "Side"
        TabLayoutMediator(tabLayout,viewpager2){
            tab,position->
            if(position == 0)
                tab.text = "Pizza"
            if(position == 1)
                tab.text = "Sides"
            //tabLayout.selectTab(tabLayout.getTabAt(viewpager2.currentItem+1))
        }.attach()
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                if (tab != null) {
//                    viewpager2.setCurrentItem(tab.position)
//                    viewpager2.currentItem = tab.position
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//        })
//        drawable = getDrawable(R.drawable.);
//        bar.setBackgroundDrawable(drawable)
        // If Opening the application for the first time first sign up then only load main activity, and if already have an account then sign in
    /*    if(!sharedPreferences.contains("SignedUp")){
            val editor = sharedPreferences.edit()
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
            editor.putBoolean("SignedUp",true)
        }*/

    }
    fun pizzaMenu(view: View){
//        pizzaButton.setBackgroundResource(R.drawable.icon_clicked)
//        cartButton.setBackgroundResource(R.drawable.icon_back)
//        val intent = Intent(this,MainActivity::class.java)
//        startActivity(intent)
    }
    fun cartMenu(view: View){
        cartButton.setBackgroundResource(R.drawable.icon_clicked)
        pizzaButton.setBackgroundResource(R.drawable.icon_back)
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }
}