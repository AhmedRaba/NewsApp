package com.training.newsapp.ui.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.training.newsapp.data.LanguagePreferences
import com.training.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var languagePreferences: LanguagePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        languagePreferences = LanguagePreferences(this)

        lifecycleScope.launch {
            languagePreferences.selectedLanguage.collect { languageCode ->
                languageCode?.let {
                    setLocale(it)
                }
            }
        }

    }

    override fun attachBaseContext(newBase: Context?) {

        val languageCode = runBlocking {
            val prefs = LanguagePreferences(newBase!!)
            prefs.selectedLanguage.firstOrNull() ?: "en"
        }


        val config = newBase?.resources?.configuration ?: Configuration()
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        config.setLocale(locale)

        val context = newBase?.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)


        val layoutDirection = if (languageCode == "ar") {
            View.LAYOUT_DIRECTION_RTL
        } else {
            View.LAYOUT_DIRECTION_LTR
        }

        binding.root.layoutDirection = layoutDirection
    }

}