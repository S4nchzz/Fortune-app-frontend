package com.fortune.app.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.fortune.app.R
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.ui.view.user.UProfileActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        adjustScreenInsets()

        setFieldResetErrorListeners()

        findViewById<TextView>(R.id.span_login).setOnClickListener {
            val openLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(openLogin)
            finish()
        }

        findViewById<ImageButton>(R.id.back_event_arrow).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            registerServerRequest()
        }
    }

    private fun setFieldResetErrorListeners() {
        val identityDocumentLayout = findViewById<TextInputLayout>(R.id.identityDocumentLayout)
        val identityDocumentField = findViewById<TextInputEditText>(R.id.register_data_identityDocument)

        identityDocumentField.addTextChangedListener {
            identityDocumentLayout.error = null
        }
    }

    private fun registerServerRequest() {
        val identity_document: String = findViewById<EditText>(R.id.register_data_identityDocument).text.toString().trim()
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