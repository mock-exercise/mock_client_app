package com.example.clientapp.data.model.ui

import android.os.Message
import com.example.clientapp.utils.Constant

data class HealthGeneral(
    val healthStatus: Constant.HealthGeneralType = Constant.HealthGeneralType.SUGGEST,
    val message: String = ""
)