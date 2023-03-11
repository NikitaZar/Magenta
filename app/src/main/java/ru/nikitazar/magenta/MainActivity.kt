package ru.nikitazar.magenta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.nikitazar.magenta.ui.SplashFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = (supportFragmentManager.findFragmentById(R.id.nav_home_fragment) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)
        val tab = findViewById<TabLayout>(R.id.toolbarTabs).apply {
            addOnTabSelectedListener(
                object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        navController.navigate(
                            when (tab.position) {
                                0 -> R.id.imageListFragment
                                1 -> R.id.favoriteFragment
                                else -> throw IllegalArgumentException("Unknown tab destination!")
                            }
                        )
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                    override fun onTabReselected(tab: TabLayout.Tab?) = Unit
                }
            )
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.favoriteFragment) {
                findViewById<Toolbar>(R.id.toolbar).navigationIcon = null
            }

            tab.isVisible = when (destination.id) {
                R.id.splashFragment -> false
                else -> true
            }
        }
    }
}