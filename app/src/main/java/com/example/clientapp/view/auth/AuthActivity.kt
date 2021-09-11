package com.example.clientapp.view.auth

import com.example.clientapp.base.BaseActivity
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityAuthBinding
import com.example.clientapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private val viewModel: AuthViewModel by viewModels()



    override fun getActivityBinding(layoutInflater: LayoutInflater) = ActivityAuthBinding.inflate(layoutInflater)

    override fun getNavHostFragment() = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

    override fun handleTask() {
        initListener()
    }

    private fun initListener() {
        lifecycle.addObserver(viewModel)
    }
}