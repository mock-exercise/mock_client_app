package com.example.clientapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.clientapp.R
import com.example.clientapp.R.*

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    companion object{
        fun startActivityAnimation(context: Context) {
            if (context is AppCompatActivity) {
                context.overridePendingTransition(anim.slide_in_right, anim.slide_out_left)
            }
        }
    }

    protected lateinit var binding: VB
    protected lateinit var controller: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding(layoutInflater)
        setContentView(binding.root)

        navHostFragment = getNavHostFragment()
        controller = navHostFragment.findNavController()

        handleTask()
    }

    abstract fun getActivityBinding(layoutInflater: LayoutInflater): VB

    abstract fun getNavHostFragment(): NavHostFragment

    abstract fun handleTask()
}