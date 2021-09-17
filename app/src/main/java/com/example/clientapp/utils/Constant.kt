package com.example.clientapp.utils

object Constant {

    enum class NotifyDialogType{
        ERROR, SUCCESS, WARNING, NOTIFY
    }

    enum class HealthGeneralType{
        UNSAFE, SAFE, SUGGEST
    }

    // data base
    const val HealthyIndex = 4
    const val DaysMakeYouInCase = 3

    enum class StatusCovid(val numberIndex: Int){
        CASE(1),
        DEATH(2) ,
        RECOVERED(3)
    }
}