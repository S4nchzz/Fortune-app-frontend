package com.fortune.app.ui.view.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.data.secure.TokenManager
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.auth.Auth_ViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)
        adjustScreenInsets()

        findViewById<ImageView>(R.id.go_back).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        
        manageChangeEvents()
    }

    private fun manageChangeEvents() {
        val passLayout1 = findViewById<TextInputLayout>(R.id.passwordField1Layout)
        val passField1 = findViewById<EditText>(R.id.passwordField1Field)

        val passLayout2 = findViewById<TextInputLayout>(R.id.passwordField2Layout)
        val passField2 = findViewById<EditText>(R.id.passwordField2Field)

        var field1HasContent = false
        var field2HasContent = false
        val changeButton = findViewById<Button>(R.id.change_pass_btn)

        passField1.addTextChangedListener {
            if (passField1.text.toString().isEmpty()) {
                field1HasContent = false
                changeButton.isEnabled = false
            } else {
                field1HasContent = true

                changeButton.isEnabled = field2HasContent // If passField2 is pressed after passField1 this will be true, this warning has no effect
            }
        }

        passField2.addTextChangedListener {
            if (passField2.text.toString().isEmpty()) {
                field2HasContent = false
                changeButton.isEnabled = false
            } else {
                field2HasContent = true

                changeButton.isEnabled = field1HasContent
            }
        }

        changeButton.setOnClickListener {
            if (passField1.text.toString() != passField2.text.toString()) {
                SuccessOrFail_Dialog(true, "Las contraseñas no coinciden.").show(supportFragmentManager, "Passwords doesnt match")
            }

            val authViewmodel: Auth_ViewModel by viewModels()

            authViewmodel.changePasswordState.observe(this) { changePasswordState ->
                when(changePasswordState) {
                    is DefaultState.Success -> {
                        SuccessOrFail_Dialog(false, "Contraseña cambiada correctamente, se cerrara la sesion."){
                            val openMain = Intent(this@ChangePasswordActivity, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }

                            startActivity(openMain)
                            finish()
                        }.show(supportFragmentManager, "Password changed")
                    }

                    is DefaultState.Error -> {
                        SuccessOrFail_Dialog(false, "Ha ocurrido un error al cambiar la contraseña."){
                            finish()
                        }.show(supportFragmentManager, "Password changed")
                    }
                }
            }

            authViewmodel.changePassword(passField1.text.toString())
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