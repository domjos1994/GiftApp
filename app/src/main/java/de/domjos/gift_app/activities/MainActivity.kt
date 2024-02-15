package de.domjos.gift_app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import de.domjos.gift_app.Helper
import de.domjos.gift_app.R
import de.domjos.gift_app.databinding.ActivityMainBinding
import de.domjos.gift_app.fragments.MainFragment
import de.domjos.gift_app.fragments.TestFragment
import de.domjos.gift_app.services.Settings

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(Helper.showHtml(getString(R.string.impress_content)))

        return when (item.itemId) {
            R.id.action_impress -> {
                alert.create().show()
                return true
            }
            R.id.action_reset -> {
                Settings(this.applicationContext).reset()

                Toast.makeText(this.applicationContext, R.string.reset_msg, Toast.LENGTH_LONG).show()

                val fragmentManager = supportFragmentManager
                val hostFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                val childFragmentManager = hostFragment?.childFragmentManager
                val fragments = childFragmentManager?.fragments
                fragments?.forEach { fItem ->
                    if(fItem is TestFragment) {
                        fItem.reset()
                    }
                    if(fItem is MainFragment) {
                        fItem.showButtonText()
                    }
                }

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}