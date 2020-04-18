package com.riccardocalligaro.imdbmovies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.riccardocalligaro.imdbmovies.R
import kotlinx.android.synthetic.main.nav_host_activity.*

class NavHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_host_activity)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        // get the nav controller from the fragment
        val navController = nav_host_fragment.findNavController()
        // set it to the bottom nav view
        bottom_nav_view.setupWithNavController(navController)
    }
}
