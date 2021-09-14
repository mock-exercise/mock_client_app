package com.example.clientapp.view.main.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.clientapp.databinding.DialogDeclareHealthBinding
import com.example.clientapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDeclareDialog: DialogFragment(), CompoundButton.OnCheckedChangeListener {

    private lateinit var binding : DialogDeclareHealthBinding

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDeclareHealthBinding.inflate(layoutInflater)

        handleTask()

        return AlertDialog.Builder(activity).apply {
            setView(binding.root)
        }.create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 0))
    }

    private fun handleTask() {
        initListener()
//        initView()
    }

    private fun initListener() {
        binding.cb0.setOnCheckedChangeListener (this)
        binding.cb1.setOnCheckedChangeListener (this)
        binding.cb2.setOnCheckedChangeListener (this)
        binding.cb3.setOnCheckedChangeListener (this)
        binding.cb4.setOnCheckedChangeListener (this)


        binding.btnInsert.setOnClickListener {
            mViewModel.insertHealth()
            dismiss()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when(buttonView){
            binding.cb0 ->{
                mViewModel.addSymptom(0)
            }
            binding.cb1 ->{
                mViewModel.addSymptom(1)
            }
            binding.cb2 ->{
                mViewModel.addSymptom(2)
            }
            binding.cb3 ->{
                mViewModel.addSymptom(3)
            }
            binding.cb4 ->{
                mViewModel.addSymptom(4)
                if(isChecked){
                    handleCheckboxView(false)
                }else{
                    handleCheckboxView(true)
                }
            }
        }
    }

    private fun handleCheckboxView(checked: Boolean){
        binding.cb0.isChecked = checked
        binding.cb1.isChecked = checked
        binding.cb2.isChecked = checked
        binding.cb3.isChecked = checked

        if(!checked){
            binding.cb0.isEnabled = checked
            binding.cb1.isEnabled = checked
            binding.cb2.isEnabled = checked
            binding.cb3.isEnabled = checked
        }
    }

    companion object{
        const val TAG = "Declare healthy"

        fun newInstance() = AddDeclareDialog()
    }
}