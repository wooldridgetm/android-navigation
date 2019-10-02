/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.navigation

import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import debug

/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
class MainActivity : AppCompatActivity()
{
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFrag: Fragment? = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        val navHostFragment: NavHostFragment = navHostFrag as NavHostFragment? ?: return
        val navController: NavController = navHostFragment.navController

        // this only happens on "navigation_activity.xml" & w960dp/"navigation_activity"
        setupNavigationMenu(navController)

        // this only happens on phones where h >= 470
        setupBottomNavMenu(navController)

        // Set up Action Bar
        //appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph) // default behavior
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest, R.id.deeplink_dest), drawerLayout)
        setupActionBar(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try
            {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException)
            {
                Integer.toString(destination.id)
            }

            //Toast.makeText(this@MainActivity, "Navigated to $dest", Toast.LENGTH_SHORT).show()
            debug( "Navigated to $dest")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        // The NavigationView already has these same navigation items, so we only add
        // navigation items to the menu here if there isn't a NavigationView
        if (navigationView == null)
        {
            menuInflater.inflate(R.menu.overflow_menu, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        // Have the NavigationUI look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.
        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)
        // DONE END STEP 9.2
    }

    private fun setupNavigationMenu(navController: NavController)
    {
        // this does NOT modify the actionBar
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
    }

    private fun setupBottomNavMenu(navController: NavController)
    {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    private fun setupActionBar(navController: NavController, appBarConfig: AppBarConfiguration)
    {
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to show the up arrow or drawer menu icon
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean
    {
        // Allows NavigationUI to support proper up navigation or the drawer layout drawer menu, depending on the situation
        //findNavController(R.id.my_nav_host_fragment).navigateUp()
        //return NavigationUI.navigateUp(drawerLayout, findNavController(R.id.my_nav_host_fragment))

        // TODO STEP 9.7 - Have NavigationUI handle up behavior in the ActionBar
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }
    // DONE END STEP 9.6
}
