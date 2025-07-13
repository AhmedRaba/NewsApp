package com.silverkey.newsapp


import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.silverkey.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        navView.setOnItemSelectedListener { item ->
            val currentDestination = navController.currentDestination?.id
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (currentDestination != R.id.navigation_home) {
                        navController.popBackStack(R.id.navigation_home, false)
                        navController.navigate(R.id.navigation_home)
                    }
                    true
                }

                R.id.navigation_saved -> {
                    if (currentDestination != R.id.navigation_saved) {
                        navController.popBackStack(R.id.navigation_saved, false)
                        navController.navigate(R.id.navigation_saved)
                    }
                    true
                }

                R.id.navigation_profile -> {
                    if (currentDestination != R.id.navigation_profile) {
                        navController.popBackStack(R.id.navigation_profile, false)
                        navController.navigate(R.id.navigation_profile)
                    }
                    true
                }

                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            val splashColor = ContextCompat.getColor(this, R.color.color_splash_background)

            when (destination.id) {
                R.id.navigation_home -> {
                    navView.menu.findItem(R.id.navigation_home).isChecked = true
                }

                R.id.navigation_saved -> {
                    navView.menu.findItem(R.id.navigation_saved).isChecked = true
                }

                R.id.navigation_profile -> {
                    navView.menu.findItem(R.id.navigation_profile).isChecked = true
                }
            }

            if (destination.id == R.id.splashFragment) {
                navView.visibility = View.GONE
                insetsController.isAppearanceLightStatusBars = false

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                        val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                        val navigationBarInsets =
                            insets.getInsets(WindowInsets.Type.navigationBars())
                        view.setBackgroundColor(splashColor)
                        view.setPadding(0, statusBarInsets.top, 0, navigationBarInsets.bottom)
                        insets
                    }
                } else {
                    window.statusBarColor = splashColor
                    window.navigationBarColor = splashColor
                }
            } else {
                navView.visibility = View.VISIBLE
                insetsController.isAppearanceLightStatusBars = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                        val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                        val navigationBarInsets =
                            insets.getInsets(WindowInsets.Type.navigationBars())
                        view.setBackgroundColor(
                            ContextCompat.getColor(
                                this,
                                R.color.color_navigation_bar
                            )
                        )
                        view.setPadding(0, statusBarInsets.top, 0, navigationBarInsets.bottom)
                        insets
                    }
                } else {
                    window.statusBarColor =
                        ContextCompat.getColor(this, R.color.color_navigation_bar)
                    window.navigationBarColor =
                        ContextCompat.getColor(this, R.color.color_navigation_bar)
                }
            }
        }
    }

}