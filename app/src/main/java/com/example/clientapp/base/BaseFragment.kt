package com.example.clientapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding> :
    Fragment() {
    private var _binding: B? = null
    val binding get() = _binding!!
    protected lateinit var controller: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getFragmentBinding(layoutInflater, container, false)
        controller = findNavController()

        handleTasks()

        return binding.root
    }

    abstract fun handleTasks()
    abstract fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    ): B

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}