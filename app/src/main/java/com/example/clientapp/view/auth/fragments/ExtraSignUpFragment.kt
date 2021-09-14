package com.example.clientapp.view.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clientapp.R
import com.example.clientapp.base.BaseFragment
import com.example.clientapp.databinding.FragmentExtraSignUpBinding

class ExtraSignUpFragment : BaseFragment<FragmentExtraSignUpBinding>() {
    override fun handleTasks() {

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    ): FragmentExtraSignUpBinding = FragmentExtraSignUpBinding.inflate(inflater, container, false)
}