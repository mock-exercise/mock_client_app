package com.example.clientapp.view.main.dialogs

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.clientapp.databinding.DialogPickImageBinding
import com.example.clientapp.utils.FuncExtension.encodeImage
import com.example.clientapp.view.main.MainActivity
import com.example.clientapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PickPictureDialog :DialogFragment(){

    private lateinit var binding : DialogPickImageBinding

    private val mActivity by lazy {
        activity as MainActivity
    }

    val mViewModel: MainViewModel by activityViewModels()
    private var photoFile: File?= null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPickImageBinding.inflate(layoutInflater)

        handleTask()

        return AlertDialog.Builder(mActivity).apply {
            setView(binding.root)
        }.create()
    }

    private fun handleTask() {
        initListener()
    }

    private fun initListener() {
        binding.layoutCamera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.layoutGallery.setOnClickListener {
            checkPermission()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera mActivity to handle the intent
            takePictureIntent.resolveActivity(mActivity.packageManager)?.also {
                // Create the File where the photo should go
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        mActivity,
                        "com.example.clientapp.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
            mViewModel.saveImageData(imageBitmap.encodeImage())
        }else if(requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK){
            val uri = data?.data
            mViewModel.saveImageData(MediaStore.Images.Media.getBitmap(context?.contentResolver, uri).encodeImage())
        }
    }

    private fun checkPermission(){
        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                mActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE
            )
        }else{
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSION_CODE ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }else{
                    if(ActivityCompat.shouldShowRequestPermissionRationale(mActivity , Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Toast.makeText(context, "You should allow this permission to read SMS message", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "OOPS! Permission is forever Denied. You can't read SMS message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object{

        const val REQUEST_IMAGE_CAPTURE = 1
        const val PICK_IMAGE = 2
        const val PERMISSION_CODE = 3

        const val TAG = "pick_picture"

        fun newInstance(): DialogFragment {
            return PickPictureDialog()
        }
    }
}