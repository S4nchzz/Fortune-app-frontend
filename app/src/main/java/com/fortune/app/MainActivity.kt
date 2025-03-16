package com.fortune.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.ui.view.auth.LoginActivity
import com.fortune.app.ui.view.auth.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        adjustScreenInsets()

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val openLogin = Intent(this, LoginActivity::class.java)
            startActivity(openLogin)
        }

        findViewById<Button>(R.id.btn_sing_up).setOnClickListener {
            val openRegister: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(openRegister)
        }
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}