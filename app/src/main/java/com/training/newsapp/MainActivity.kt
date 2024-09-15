package com.training.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.training.newsapp.databinding.ActivityMainBinding
import com.training.newsapp.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}