package com.example.clientapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId : Int) : Fragment(){

    private var _binding : T? = null
    val binding : T get() = _binding!!

    protected lateinit var controller: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        controller = findNavController()

        handleTasks()
        return binding.root
    }

    abstract fun handleTasks()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}