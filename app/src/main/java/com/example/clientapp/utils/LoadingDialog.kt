package com.example.clientapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.WindowManager
import com.example.clientapp.R

class LoadingDialog(private val activity : Activity) {
    private lateinit var dialog: AlertDialog

    fun startLoading()
    {
        //set view
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.item_loading, null)
        //** set dialog */
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()

        val window = dialog.window
        val wlp = window?.attributes
        wlp?.let {
            it.gravity = Gravity.CENTER
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.WRAP_CONTENT
//            it.windowAnimations = R.style.dialogAnimation
        }
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 90)
        window?.attributes = wlp
        window?.setBackgroundDrawable(inset)

        dialog.show()
    }

    fun dismissDialog()
    {
//        dialog.dismiss()
    }
}