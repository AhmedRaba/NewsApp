package com.training.newsapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.training.newsapp.data.LanguagePreferences
import com.training.newsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var languagePreferences: LanguagePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen early
        installSplashScreen()

        // Initialize language preferences
        languagePreferences = LanguagePreferences(this)

        // Apply the language before the layout is inflated
        lifecycleScope.launch {
            languagePreferences.selectedLanguage.collect { languageCode ->
                languageCode?.let {
                    setLocale(it)
                }
            }
        }

        super.onCreate(savedInstanceState)

        // Inflate layout after setting locale
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun attachBaseContext(newBase: Context?) {
        // Initialize LanguagePreferences with runBlocking to access the selected language synchronously
        val languageCode = runBlocking {
            val prefs = LanguagePreferences(newBase!!)
            prefs.selectedLanguage.firstOrNull() ?: "en" // Fallback to English if no language is set
        }

        // Set the locale with the retrieved language code
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        val context = newBase?.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Set layout direction if needed
        val layoutDirection = if (languageCode == "ar") {
            View.LAYOUT_DIRECTION_RTL
        } else {
            View.LAYOUT_DIRECTION_LTR
        }

        binding.root.layoutDirection = layoutDirection
    }

}