package com.fortune.app.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        adjustScreenInsets()

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginServerRequest()
        }
    }

    private fun loginServerRequest() {
        val dni_nie: String = findViewById<EditText>(R.id.login_data_dniNie).text.toString()
        val password: String = findViewById<EditText>(R.id.login_data_password).text.toString()

        val alertDialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_dialog)
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            AuthService.login(dni_nie, password) { user ->
                CoroutineScope(Dispatchers.Main).launch {
                    alertDialog.dismiss()
                    if (user != null) {
                        user.digitalSign?.let {
                            //Open main app view
                        } ?: run {
                            val openPinActivity = Intent(this@LoginActivity, PinActivity::class.java)
                            startActivity(openPinActivity)
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