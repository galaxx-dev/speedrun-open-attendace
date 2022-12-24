package com.arysugiarto.attendence.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.arysugiarto.attendence.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    companion object {
        fun Fragment.reInflateMainNavGraph() {
            val fragmentHost = activity?.supportFragmentManager
                ?.findFragmentById(R.id.mainNavHostContainer)

            val navHost = fragmentHost as? NavHostFragment

            val navController = navHost?.navController
            val navInflater = navController?.navInflater

            navInflater?.inflate(R.navigation.nav_auth_graph)
                ?.apply { startDestination = R.id.main_fragment }
                ?.let { navController.graph = it }
        }

        fun Fragment.reInflateLoginNavGraph() {
            val fragmentHost = activity?.supportFragmentManager
                ?.findFragmentById(R.id.mainNavHostContainer)

            val navHost = fragmentHost as? NavHostFragment

            val navController = navHost?.navController
            val navInflater = navController?.navInflater

            navInflater?.inflate(R.navigation.nav_auth_graph)
                ?.apply { startDestination = R.id.auth }
                ?.let { navController.graph = it }
        }


        const val LOGIN_ATTEMPT_KEY = "login_attempt"
    }
}