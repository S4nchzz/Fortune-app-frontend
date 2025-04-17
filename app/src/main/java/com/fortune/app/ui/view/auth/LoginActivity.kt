package com.fortune.app.ui.view.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.fortune.app.R
import com.fortune.app.ui.dialogs.IncorrectCredentials_Dialog
import com.fortune.app.ui.view.MainAppActivity
import com.fortune.app.domain.state.LoginState
import com.fortune.app.ui.view.user.PinActivity
import com.fortune.app.ui.viewmodel.auth.Auth_ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        adjustScreenInsets()

        findViewById<ImageButton>(R.id.back_event_arrow).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<TextView>(R.id.span_register).setOnClickListener {
            val openRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(openRegister)
            finish()
        }

        setFieldResetErrorListeners()

        loadingDialog = AlertDialog.Builder(this@LoginActivity)
            .setView(R.layout.dialog_loading)
            .create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.setCancelable(false)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginServerRequest()
        }
    }

    private fun loginServerRequest() {
        val identityDocument: String = findViewById<EditText>(R.id.login_data_dniNie).text.toString()
        val password: String = findViewById<EditText>(R.id.login_data_password).text.toString()

        if (!validateFields(identityDocument, password)) {
            return
        }

        loadingDialog.show()

        val credentialFailDialog = IncorrectCredentials_Dialog()

        val authViewModel: Auth_ViewModel by viewModels()
        authViewModel.login.observe(this) { loginState ->
            if (loginState != null) {
                when (loginState) {
                    is LoginState.Error -> {
                        loadingDialog.dismiss()

                        authViewModel.resetLoginObserve()
                        credentialFailDialog.show(supportFragmentManager, "Incorrect credentials")
                    }

                    is LoginState.Success -> {
                        loadingDialog.dismiss()
                        if (loginState.responseInfo.hasDigitalSign) {
                            val openMainAppActivity = Intent(this@LoginActivity, MainAppActivity::class.java)
                            startActivity(openMainAppActivity)
                        } else {
                            val openPinActivity = Intent(this@LoginActivity, PinActivity::class.java)
                            startActivity(openPinActivity)
                        }

                        finish()
                    }
                }
            }
        }

        authViewModel.login(identityDocument, password)
    }

    private fun validateFields(identityDocument: String, password: String): Boolean {
        val identityDocumentLayout = findViewById<TextInputLayout>(R.id.login_data_dniNie_layout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.login_data_password_layout)

        var validate = true
        if (identityDocument.isEmpty()) {
            identityDocumentLayout.error = "Este campo no puede estar vacio."
            validate = false
        }

        if (password.isEmpty()) {
            passwordLayout.error = "Este campo no puede estar vacio."
            validate = false
        }

        return validate
    }

    private fun setFieldResetErrorListeners() {
        val identityDocumentLayout = findViewById<TextInputLayout>(R.id.login_data_dniNie_layout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.login_data_password_layout)

        findViewById<TextInputEditText>(R.id.login_data_dniNie).addTextChangedListener {
            identityDocumentLayout.error = null
        }

        findViewById<TextInputEditText>(R.id.login_data_password).addTextChangedListener {
            passwordLayout.error = null
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