package com.example.clientapp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.clientapp.utils.FuncExtension.convertBase64ToBitmap
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("setImage")
fun CircleImageView.loadImageByStringCode(stringCode: String){

    if(stringCode == "") return
    val bitmap = stringCode.convertBase64ToBitmap()
    setImageBitmap(bitmap)
}