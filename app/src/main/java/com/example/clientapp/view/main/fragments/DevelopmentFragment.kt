package com.example.clientapp.view.main.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentDevelopmentBinding

class DevelopmentFragment : BaseFragment<FragmentDevelopmentBinding>(R.layout.fragment_development) {

    override fun handleTasks() {
        (binding.imgAvatar.background as AnimationDrawable).start()
    }
}