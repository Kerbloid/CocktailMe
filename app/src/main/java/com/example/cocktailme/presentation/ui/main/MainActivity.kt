package com.example.cocktailme.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.cocktailme.R
import com.example.cocktailme.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_cocktails -> {
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_cocktails)
                    true
                }
                R.id.navigation_favorite -> {
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_favorite)
                    true
                }
                else -> {
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_search)
                    true
                }
            }
        }
    }
}