package com.fortune.app.ui.view.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.data.entities.user.UserEntity
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import com.fortune.app.ui.viewmodel.user.UProfile_ViewModel
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        val identity_document: String = findViewById<EditText>(R.id.register_data_dniNie).text.toString().trim()
        val email: String = findViewById<EditText>(R.id.register_data_email).text.toString().trim()
        val password: String = findViewById<EditText>(R.id.register_data_password).text.toString().trim()
        val confirmPassword: String = findViewById<EditText>(R.id.register_data_confirm_password).text.toString().trim()

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return;
        }

        val userDTO = UserDTO(identity_document, email, password)

        val openUProfile = Intent(this, UProfileActivity::class.java)
        openUProfile.putExtra("userDTO", userDTO)

        startActivity(openUProfile)
        finish()
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}