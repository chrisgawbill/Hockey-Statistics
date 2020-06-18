package com.example.hockeystatistics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                if(AppCompatDelegate.getDefaultNightMode() ==  AppCompatDelegate.MODE_NIGHT_YES){
                        setTheme(R.style.DarkTheme)
                }
                setContentView(R.layout.activity_main)
                val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
                val navController = host.navController
                findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
                var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    }
}