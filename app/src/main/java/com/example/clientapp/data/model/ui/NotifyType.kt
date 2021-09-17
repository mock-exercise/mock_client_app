package com.example.clientapp.data.model.ui

import com.example.clientapp.utils.Constant.NotifyDialogType

data class NotifyType (
    val type: NotifyDialogType = NotifyDialogType.ERROR,
    val message: String = "")