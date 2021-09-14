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

    private var dialogType: Int? = null
    private var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dialogType = it.getInt(ARG_DIALOG_TYPE)
            content = it.getString(ARG_CONTENT)
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
        binding.txtMessage.text = content
    }

    private fun initListener() {
        binding.btnOk.setOnClickListener {
            dismiss()
        }
    }

    private fun showIcon() {
        when(dialogType){
            Constant.NotifyDialogType.SUCCESS.ordinal -> {
                binding.imgIcon.setImageResource(R.drawable.ic_dialog_tick)
            }
            Constant.NotifyDialogType.ERROR.ordinal -> {
                binding.imgIcon.setImageResource(R.drawable.ic_dialog_error)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 0))
    }

    companion object{
        const val TAG = "myDialog"
        private const val ARG_CONTENT = "arg_content"
        private const val ARG_DIALOG_TYPE = "arg_dialog_type"

        fun newInstance( dialogType: Int, content: Boolean) = NotifyDialog().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_CONTENT, content)
                putInt(ARG_DIALOG_TYPE, dialogType)
            }
        }
    }
}