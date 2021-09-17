package com.example.clientapp.view.auth

import android.content.Context
import android.content.Intent
import com.example.clientapp.base.BaseActivity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityAuthBinding
import com.example.clientapp.data.repository.localsource.DataStoreManager
import com.example.clientapp.utils.LoadingDialog
import com.example.clientapp.view.main.MainActivity
import com.example.clientapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    companion object {
        val TAG: String = AuthActivity::class.java.simpleName

        fun start(context: Context) {
            if (context is AppCompatActivity) {
                val intent = Intent(context, AuthActivity::class.java)
                context.startActivity(intent)
                startActivityAnimation(context)
            }
        }
    }

    private val mViewModel: AuthViewModel by viewModels()
    private val dialog: LoadingDialog by lazy { LoadingDialog(this) }

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun getActivityBinding(layoutInflater: LayoutInflater) =
        ActivityAuthBinding.inflate(layoutInflater)

    override fun getNavHostFragment() =
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

    override fun handleTask() {
        checkTokenUser()

        initListener()
        initObserve()
    }

    private fun checkTokenUser() {
        dataStoreManager.accessToken.asLiveData().observe(this, {
            if (it != null) {
                finish()
                MainActivity.start(this)
            }
        })
    }

    private fun initObserve() {
        mViewModel.eventLoading.observe(this, { eventLoading ->
            if (eventLoading.getContentIfNotHandled() == true) {

                dialog.startLoading()
            } else {
                dialog.dismissLoading()
            }
        })

        mViewModel.eventError.observe(this, { eventError ->
            Toast.makeText(this, eventError.getContentIfNotHandled(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListener() {
        lifecycle.addObserver(mViewModel)
    }
}