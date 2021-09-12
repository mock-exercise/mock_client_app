package com.example.clientapp.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import com.example.clientapp.R
import com.example.clientapp.base.BaseActivity
import com.example.clientapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        val TAG: String = MainActivity::class.java.simpleName

        fun start(context: Context){
            if(context is AppCompatActivity){
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                startActivityAnimation(context)
            }
        }
    }

    override fun getActivityBinding(layoutInflater: LayoutInflater)= ActivityMainBinding.inflate(layoutInflater)

    override fun getNavHostFragment()=
        supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment

    override fun handleTask() {

    }
}