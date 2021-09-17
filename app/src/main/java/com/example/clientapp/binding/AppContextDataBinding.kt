package com.example.clientapp.binding

import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.example.clientapp.R
import com.example.clientapp.utils.Constant.HealthGeneralType

class AppContextDataBinding

@BindingAdapter("type")
fun AppCompatButton.setFormattedText(type: HealthGeneralType) {
   when(type){
       HealthGeneralType.SAFE -> {
           setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_dialog_tick), null, null, null)
           setBackgroundColor(context.getColor(R.color.txt_status_healthy))
       }
        HealthGeneralType.SUGGEST -> {
           setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_main_warning), null, null, null)
           setBackgroundColor(context.getColor(R.color.txt_status_not_healthy))
       }
       HealthGeneralType.UNSAFE -> {
           setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_main_warning), null, null, null)
           setBackgroundColor(context.getColor(R.color.txt_status_warning))
       }
   }
}