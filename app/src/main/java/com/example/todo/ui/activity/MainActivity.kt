package com.example.todo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.todo.R
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.ui.App

class MainActivity : AppCompatActivity() {
    private lateinit var controller: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        controller = navHostFragment.navController

        // Set up the bottom navigation view with the nav controller
        binding.bottomNav.setupWithNavController(controller)

        // Hide the bottom navigation view when the user is on the onBoardFragment
        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.onBoardFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

        if (!App.prefs.isBoardShow()) {
            controller.navigate(R.id.onBoardFragment)
        }

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.noteFragment -> {
                    controller.navigate(R.id.noteFragment)
                    true
                }
                R.id.contactFragment -> {
                    controller.navigate(R.id.contactFragment)
                    true
                }
                R.id.profileFragment -> {
                    controller.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}