package com.example.solveitproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private val bottomNavigationView: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //set up the ToolBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.navHostMain) as? NavHostFragment
        navController = navHostFragment?.navController

        navController?.let { NavigationUI.setupActionBarWithNavController(this, it) }


        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.mainActivityBottomNavigationView)
        navController?.let { NavigationUI.setupWithNavController(bottomNavigationView, it) }
    }

    private var isAddMenuItemVisible = true


    //    private var isAddMenuItemVisible = true
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        val addMenuItem = menu?.findItem(R.id.menuItemActionBarAddStudent)
        addMenuItem?.isVisible = isAddMenuItemVisible
        return true
    }

    fun setAddMenuItemVisibility(isVisible: Boolean) {
        isAddMenuItemVisible = isVisible
        invalidateOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController?.navigateUp()
                true
            }
            R.id.menuItemActionBarAddStudent -> {
                navController?.navigate(R.id.action_global_addPostFragment)
                true
            }

            else -> NavigationUI.onNavDestinationSelected(item, navController!!)
        }
    }

    fun setBottomBarVisibility(isVisible: Boolean) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainActivityBottomNavigationView)

        if (isVisible) {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }
    }

}