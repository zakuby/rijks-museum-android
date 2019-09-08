package nl.rijksmuseum.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.rijksmuseum.R
import nl.rijksmuseum.screens.museum.view.MuseumActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        navigateToMuseum()
    }

    private fun navigateToMuseum() {
        val i = Intent(this, MuseumActivity::class.java)
        startActivity(i)
        finish()
    }
}