package com.example.clientapp.view.main.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentDevelopmentBinding

class DevelopmentFragment : Fragment() {

    private lateinit var binding: FragmentDevelopmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_development,
            container,
            false
        )

        handleTasks()

        return binding.root
    }

    private fun handleTasks() {
        (binding.imgAvatar.background as AnimationDrawable).start()
    }
}