package com.example.pizzanation.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pizzanation.R
import com.example.pizzanation.databinding.ActivitySignInBinding

class SignIn : AppCompatActivity() {
    lateinit var dataBinding: ActivitySignInBinding
    lateinit var sinInVM: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_in)

        sinInVM = ViewModelProvider(this).get(SignInViewModel::class.java)

        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)
        dataBinding.viewmodel = sinInVM
        dataBinding.lifecycleOwner = this


    }
}