package com.fortune.app.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.api.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        adjustScreenInsets()

        findViewById<Button>(R.id.btn_sing_up).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val nif_nie: String = findViewById<EditText>(R.id.login_data_nifnie).text.toString()
            val password: String = findViewById<EditText>(R.id.login_data_password).text.toString()

            val alertDialog = AlertDialog.Builder(this)
                .setView(R.layout.alert_dialog)
                .create()

            alertDialog.setCancelable(false)
            alertDialog.show()

            CoroutineScope(Dispatchers.IO).launch {
                AuthService.login(nif_nie, password) { loginCredentials, hasDigitalSign ->

                    // Coroutine que modifica las vistas generadas del hilo main por el alert
                    CoroutineScope(Dispatchers.Main).launch {
                        alertDialog.dismiss()
                        if (loginCredentials && !hasDigitalSign) {
                            val openPinView = Intent(this@LoginActivity, PinActivity::class.java)
                            startActivity(openPinView)
                        } else if (hasDigitalSign) {
                            // LOAD MAIN APP
                        } else {
                            Toast.makeText(this@LoginActivity, "Login incorrecto", Toast.LENGTH_SHORT).show() // Modificar por alguna animacion
                        }
                    }
                }
            }
        }
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}