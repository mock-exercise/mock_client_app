package com.example.clientapp.view.main

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.clientapp.R
import com.example.clientapp.base.BaseActivity
import com.example.clientapp.databinding.ActivityMainBinding
import com.example.clientapp.data.repository.localsource.DataStoreManager
import com.example.clientapp.utils.LoadingDialog
import com.example.clientapp.utils.NotifyDialog
import com.example.clientapp.view.auth.AuthActivity
import com.example.clientapp.view.main.dialogs.AddDeclareDialog
import com.example.clientapp.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName

        fun start(context: Context) {
            if (context is AppCompatActivity) {
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
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var topLevelDestination: Set<Int>

    override fun handleTask() {
        initView()

        initObserve()
        initListener()
    }

    private fun initView() {
        // bottom view
        binding.bottomNavigationView.setupWithNavController(controller)
 
        topLevelDestination =
            setOf(R.id.chartFragment, R.id.historyDeclareFragment)
        appBarConfiguration = AppBarConfiguration(
            topLevelDestination, binding.drawerLayout
        )

        setupActionBarWithNavController(controller, appBarConfiguration)

        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chartFragment, R.id.historyDeclareFragment -> {
                    binding.fabButton.visibility = View.VISIBLE
                    binding.bottomAppBar.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomAppBar.visibility = View.INVISIBLE
                    binding.fabButton.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun initObserve() {
        dataStoreManager.accessToken.asLiveData().observe(this, {
            if (it != null) {
                mViewModel.userID = it

                mViewModel.getBasicData()
                mViewModel.getUserInformationFromServer()
            }
        })

        mViewModel.userInformation.observe(this, {
            if (!it.isActive) {
                //show dialog
            }
        })

        mViewModel.eventLoading.observe(this, { eventLoading ->
            if (eventLoading.getContentIfNotHandled() == true) {
                dialog.startLoading()
            } else {
                dialog.dismissLoading()
            }
        })

        mViewModel.eventNotify.observe(this, { event ->
            val notifyInformation = event.getContentIfNotHandled()

            notifyInformation?.let { notify ->
                NotifyDialog.newInstance(notify.type.ordinal, notify.message).show(supportFragmentManager, NotifyDialog.TAG)
            }
        })
    }

    private fun initListener() {
        lifecycle.addObserver(mViewModel)
        setNavigationViewListener()

        binding.fabButton.setOnClickListener {
            mViewModel.resetDeclareHealth()
            AddDeclareDialog.newInstance().show(supportFragmentManager, AddDeclareDialog.TAG)
        }
    }

    private fun setNavigationViewListener() {
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun getActivityBinding(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)

    override fun getNavHostFragment() =
        supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_log_out->{
                mViewModel.clearDataStore()
                AuthActivity.start(this)
                finish()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}