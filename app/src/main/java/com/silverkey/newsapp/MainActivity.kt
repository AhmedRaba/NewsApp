package com.silverkey.newsapp

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.silverkey.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            val splashColor=ContextCompat.getColor(this, R.color.color_splash_background)
            when (destination.id) {
                com.silverkey.feature.R.id.splashFragment -> {
                    navView.visibility = View.GONE
                    binding.toolbar.visibility = View.GONE

                    insetsController.isAppearanceLightStatusBars = false

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
                            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                                val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                                val navigationBarInsets = insets.getInsets(WindowInsets.Type.navigationBars())
                                view.setBackgroundColor(splashColor)

                                view.setPadding(0, statusBarInsets.top, 0, navigationBarInsets.bottom)
                                insets
                            }
                        } else {
                            window.statusBarColor = splashColor
                            window.navigationBarColor = splashColor
                        }


                }

                else -> {
                    navView.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.VISIBLE

                    insetsController.isAppearanceLightStatusBars = true

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
                        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                            val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                            val navigationBarInsets = insets.getInsets(WindowInsets.Type.navigationBars())
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.color_navigation_bar))

                            view.setPadding(0, statusBarInsets.top, 0, navigationBarInsets.bottom)
                            insets
                        }
                    } else {
                        window.statusBarColor = ContextCompat.getColor(this, R.color.color_navigation_bar)
                        window.navigationBarColor = ContextCompat.getColor(this, R.color.color_navigation_bar)
                    }
                }
            }
        }
    }
}