package com.example.pizzanation.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pizzanation.R
import com.example.pizzanation.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    lateinit var signupVM: SignUpViewModel
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)
        signupVM = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up)

        binding.viewmodel=signupVM
        binding.lifecycleOwner=this
        
    }
}