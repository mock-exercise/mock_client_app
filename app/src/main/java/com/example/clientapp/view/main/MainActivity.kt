package com.example.clientapp.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.clientapp.R
import com.example.clientapp.base.BaseActivity
import com.example.clientapp.databinding.ActivityMainBinding
import com.example.clientapp.model.localsource.DataStoreManager
import com.example.clientapp.utils.Constant
import com.example.clientapp.utils.LoadingDialog
import com.example.clientapp.view.main.dialogs.AddDeclareDialog
import com.example.clientapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        val TAG: String = MainActivity::class.java.simpleName

        fun start(context: Context){
            if(context is AppCompatActivity){
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                startActivityAnimation(context)
            }
        }
    }

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val mViewModel: MainViewModel by viewModels()
    private val dialog: LoadingDialog by lazy { LoadingDialog(this) }

    override fun getActivityBinding(layoutInflater: LayoutInflater)= ActivityMainBinding.inflate(layoutInflater)

    override fun getNavHostFragment()=
        supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment

    override fun handleTask() {
        initView()
        setUpData()

        initObserve()
        initListener()
    }

    private fun initView() {
        // bottom view
        binding.bottomNavigationView.setupWithNavController(controller)
    }

    private fun setUpData() {
        mViewModel.getBasicData()
    }

    private fun initObserve() {
        dataStoreManager.accessToken.asLiveData().observe(this, {
            if (it != null) {
                mViewModel.userID = it
                mViewModel.getUserInformation()
            }
        })

        mViewModel.userInformation.observe(this, {
            if(it.active_id == Constant.UserActive.ACTIVE.ordinal){
                //show dialog
            }
        })

        mViewModel.eventLoading.observe(this, { eventLoading ->
            if (eventLoading.getContentIfNotHandled() == true) {
                dialog.startLoading()
            } else {
                dialog.dismissDialog()
            }
        })

        mViewModel.eventError.observe(this, { eventError ->
            Toast.makeText(this, eventError.getContentIfNotHandled(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListener() {
        binding.fabButton.setOnClickListener {
            AddDeclareDialog.newInstance().show(supportFragmentManager, AddDeclareDialog.TAG)
        }
    }
}