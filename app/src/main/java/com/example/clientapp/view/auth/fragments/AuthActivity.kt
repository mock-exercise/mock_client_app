package com.example.clientapp.view.auth.fragments

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.clientapp.R
import com.example.clientapp.viewmodel.AuthViewModel
import com.example.connectorlibrary.enitity.User
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val date = "20-05-2021"
        val simpleFormat = SimpleDateFormat("dd-MM-yyyy").parse(date)!!

        viewModel.registerAccount(User(
            name = "Nam",
            phone_number = "0918780192",
            passport_number = "123",
            birthday = simpleFormat,
            gender_id = 0,
        ))
    }
}