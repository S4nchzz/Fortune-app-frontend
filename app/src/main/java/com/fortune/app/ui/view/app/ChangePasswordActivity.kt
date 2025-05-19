package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.fortune.app.R
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.google.android.material.textfield.TextInputLayout

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)
        adjustScreenInsets()

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