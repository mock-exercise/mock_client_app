package com.example.clientapp.utils

object Constant {

    enum class NotifyDialogType{
        ERROR, SUCCESS
    }

    // data base
    const val HealthyIndex = 4

    enum class StatusCovid(val numberIndex: Int){
        CASE(1),
        DEATH(2) ,
        RECOVERED(3)
    }
}