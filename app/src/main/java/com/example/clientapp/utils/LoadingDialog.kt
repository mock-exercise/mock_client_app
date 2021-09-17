package com.example.clientapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.WindowManager
import com.example.clientapp.databinding.ItemLoadingBinding

class LoadingDialog(private val activity : Activity) {
    private var dialog: AlertDialog? = null
    lateinit var binding: ItemLoadingBinding

    fun startLoading()
    {
        //set view
        val inflater = activity.layoutInflater
        binding = ItemLoadingBinding.inflate(inflater)
        (binding.imgMascot.background as AnimationDrawable).start()
        //** set dialog */
        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
        builder.setCancelable(false)
        dialog = builder.create()

        val window = dialog?.window
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

        dialog?.show()
    }

    fun dismissLoading()
    {
        dialog?.dismiss()
    }
}