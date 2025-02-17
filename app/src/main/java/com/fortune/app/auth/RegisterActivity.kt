package com.fortune.app.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        adjustScreenInsets()

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            registerServerRequest()
        }
    }

    private fun registerServerRequest() {
        val dniNie: String = findViewById<EditText>(R.id.register_data_dniNie).text.toString().trim()
        val email: String = findViewById<EditText>(R.id.register_data_email).text.toString().trim()
        val password: String = findViewById<EditText>(R.id.register_data_password).text.toString().trim()
        val confirmPassword: String = findViewById<EditText>(R.id.register_data_confirm_password).text.toString().trim()

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return;
        }

        val alertDialog: AlertDialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_dialog)
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            AuthService.register(dniNie, email, password) { user ->
                CoroutineScope(Dispatchers.Main).launch {
                    alertDialog.dismiss()
                    if (user != null) {
                        user.digitalSign?.let {
                            // Open main view
                            Toast.makeText(this@RegisterActivity, "Firma encontrada", Toast.LENGTH_SHORT).show()
                        } ?: run {
                            val intent = Intent(this@RegisterActivity, UserProfile::class.java)
                            intent.putExtra("id", user.id)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}