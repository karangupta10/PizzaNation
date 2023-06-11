package com.example.pizzanation.cart

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CartViewModelFactory(application: Application) : ViewModelProvider.Factory{
    lateinit var application:Application

    init{
        this.application = application
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(application) as T
    }
}