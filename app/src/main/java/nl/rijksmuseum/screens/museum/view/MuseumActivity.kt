package nl.rijksmuseum.screens.museum.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import nl.rijksmuseum.R
import nl.rijksmuseum.core.base.BaseActivity
import nl.rijksmuseum.databinding.ActivityMuseumBinding
import nl.rijksmuseum.screens.profile.ProfileActivity

class MuseumActivity : BaseActivity<ActivityMuseumBinding>(R.layout.activity_museum){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivity()
    }

    private fun setupActivity(){
        val toolbar = binding.toolbar
        val navController = findNavController(R.id.nav_host)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    private fun openProfile(){
        val i = Intent(this, ProfileActivity::class.java)
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_profile -> {
                openProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host).navigateUp() || super.onSupportNavigateUp()
    }
}
