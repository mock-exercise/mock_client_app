package com.example.clientapp.view.auth.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.clientapp.databinding.DialogDeclareHealthBinding

class AddDeclareDialog: DialogFragment() {

    private lateinit var binding : DialogDeclareHealthBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDeclareHealthBinding.inflate(layoutInflater)

        handleTask()

        return AlertDialog.Builder(activity).apply {
            setView(binding.root)
        }.create()
    }

    private fun handleTask() {

    }

    companion object{
        const val TAG = "Declare healthy"

        fun newInstance( message: String, isError: Boolean = false) = AddDeclareDialog()
    }
}