package com.example.clientapp.utils

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.clientapp.R
import com.example.clientapp.databinding.DialogNotifyBinding

class NotifyDialog: DialogFragment() {
    private lateinit var binding : DialogNotifyBinding

    private var isErrorDialog: Int? = null
    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isErrorDialog = it.getInt(ARG_NOTIFY_TYPE)
            message = it.getString(ARG_MESSAGE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNotifyBinding.inflate(layoutInflater)

        handleTask()

        return AlertDialog.Builder(activity).apply {
            setView(binding.root)
        }.create()
    }

    private fun handleTask() {
        showIcon()
        initListener()
        initView()
    }

    private fun initView() {
        binding.txtMessage.text = message
    }

    private fun initListener() {
        binding.btnOk.setOnClickListener {
            dismiss()
        }
    }

    private fun showIcon() {
        when(isErrorDialog){
            Constant.NotifyDialogType.SUCCESS.ordinal -> {
                binding.imgIcon.setImageResource(R.drawable.ic_dialog_tick)
            }
            Constant.NotifyDialogType.ERROR.ordinal -> {
                binding.imgIcon.setImageResource(R.drawable.ic_dialog_error)
            }
            Constant.NotifyDialogType.NOTIFY.ordinal ->{
                binding.imgIcon.setImageResource(R.drawable.ic_advice)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 0))
    }

    companion object{
        const val TAG = "Notify Dialog"
        private const val ARG_NOTIFY_TYPE= "arg_notify_type"
        private const val ARG_MESSAGE = "arg_message"

        fun newInstance( notifyType: Int, message: String) = NotifyDialog().apply {
            arguments = Bundle().apply {
                putInt(ARG_NOTIFY_TYPE, notifyType)
                putString(ARG_MESSAGE, message)
            }
        }
    }
}