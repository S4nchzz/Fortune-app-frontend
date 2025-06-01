package com.fortune.app.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
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
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
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
        if (!areAllFieldsFilled()) {
            SuccessOrFail_Dialog(true, "No deje ningun campo vacio").show(supportFragmentManager, "Register fields empty")
            return
        }

        if (!validateFields()) {
            return
        }

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

    private fun validateFields(): Boolean {
        val dniField = findViewById<TextInputEditText>(R.id.register_data_identityDocument)
        val dniLayout = findViewById<TextInputLayout>(R.id.identityDocumentLayout)

        val emailField = findViewById<TextInputEditText>(R.id.register_data_email)
        val emailLayout = findViewById<TextInputLayout>(R.id.register_data_email_layout)

        var isValid = true

        val dniRegex = Regex("^[0-9]{8}[A-Za-z]$")
        val dniText = dniField.text.toString().trim()

        if (!dniRegex.matches(dniText)) {
            dniLayout.error = "DNI inválido. Debe tener 8 números y una letra (ej. 12345678Z)"
            isValid = false
        } else {
            dniLayout.error = null
        }

        val emailText = emailField.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            emailLayout.error = "Email inválido"
            isValid = false
        } else {
            emailLayout.error = null
        }

        return isValid
    }


    private fun areAllFieldsFilled(): Boolean {
        val identityDocument: TextInputEditText = findViewById(R.id.register_data_identityDocument)
        val email: TextInputEditText = findViewById(R.id.register_data_email)
        val password: TextInputEditText = findViewById(R.id.register_data_password)
        val confirmPassword: TextInputEditText = findViewById(R.id.register_data_confirm_password)

        return !identityDocument.text.isNullOrBlank()
                && !email.text.isNullOrBlank()
                && !password.text.isNullOrBlank()
                && !confirmPassword.text.isNullOrBlank()
    }


    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}