package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.google.android.material.navigation.NavigationView

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_config)
        adjustScreenInsets()

        findViewById<ImageView>(R.id.go_back).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        manageSettingsButtons()
    }

    private fun manageSettingsButtons() {
        val navigationView = findViewById<NavigationView>(R.id.config_navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.app_info -> {
                    Toast.makeText(this@ConfigActivity, "Iyan Sanchez da Costa - 2ºDAM Proyecto final. 2025", Toast.LENGTH_LONG).show()
                    true
                }

                else -> false
            }
        }
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}