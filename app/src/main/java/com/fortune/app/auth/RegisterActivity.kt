package com.fortune.app.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        adjustScreenInInsets()

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            registerServerRequest()
        }
    }

    private fun registerServerRequest() {
        val nifnie: String = findViewById<EditText>(R.id.register_data_nifnie).text.toString()
        val email: String = findViewById<EditText>(R.id.register_data_email).text.toString()
        val password: String = findViewById<EditText>(R.id.register_data_password).text.toString()

        val alertDialog: AlertDialog = AlertDialog.Builder(this)
            .setView(R.layout.alert_dialog)
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            AuthService.register(nifnie, email, password) { user ->
                alertDialog.dismiss()
                user?.digitalSign?.let {
                    // Open main view app
                } ?: run {
                    val intent = Intent(this@RegisterActivity, PinActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun adjustScreenInInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}