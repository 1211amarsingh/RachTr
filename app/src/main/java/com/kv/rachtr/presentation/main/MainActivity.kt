package com.kv.rachtr.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.kv.rachtr.MyApplication
import com.kv.rachtr.R
import com.kv.rachtr.databinding.MainActivityBinding
import com.kv.rachtr.domain.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }
    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.welcomeFragment,
                R.id.loginFragment,
                R.id.homeFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     * If using the default action bar this must be overridden.
     * This will handle back actions initiated by the the back arrow
     * at the start of the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logoutUser()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        val datastore = (application as MyApplication).dataStoreManager
        GlobalScope.launch {
            datastore.saveUser(UserModel("","",""))
            withContext(Dispatchers.Main) {
                navController.navigate(
                    R.id.welcomeFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
                )
            }
        }
    }

}