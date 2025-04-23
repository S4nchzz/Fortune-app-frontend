package com.fortune.app.ui.view.user

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.fortune.app.R
import com.fortune.app.ui.view.MainAppActivity
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import com.fortune.app.ui.viewmodel.auth.Auth_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinActivity : AppCompatActivity() {
    private val pinValues: List<Int> = listOf(
        R.id.firma_box_1,
        R.id.firma_box_2,
        R.id.firma_box_3,
        R.id.firma_box_4,
        R.id.firma_box_5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pin)
        adjustScreenInsets()

        manageSignFocus();

        findViewById<Button>(R.id.btn_confirm_pin).setOnClickListener {
            pinCreationServerRequest()
        }
    }

    private fun pinCreationServerRequest() {
        val authViewmodel: Auth_ViewModel by viewModels()
        val accountViewModel: Account_ViewModel by viewModels()

        val alertDialog = AlertDialog.Builder(this)
            .setView(R.layout.dialog_loading)
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.show()

        if (isPinCorrect()) {
            authViewmodel.digitalSign.observe(this) {
                accountViewModel.account.observe(this) {
                    alertDialog.dismiss()

                    val openMainView = Intent(this@PinActivity, MainAppActivity::class.java)
                    startActivity(openMainView)
                    finish()
                }

                accountViewModel.createAccount()
            }

            authViewmodel.createDigitalSign(getCompletePin())
        }
    }

    private fun manageSignFocus() {
        for (i in 0 until pinValues.size - 1) {
            val et1 = findViewById<EditText>(pinValues[i])
            val et2 = findViewById<EditText>(pinValues[i + 1])

            et1.addTextChangedListener {
                if (it?.length == 1) {
                    et1.isFocusable = false
                    et1.isFocusableInTouchMode = false

                    et2.isFocusable = true
                    et2.isFocusableInTouchMode = true
                    et2.requestFocus()
                }
            }

            et2.addTextChangedListener {
                if (it?.length == 0) {
                    et1.isFocusable = true
                    et1.isFocusableInTouchMode = true

                    et2.isFocusable = false
                    et2.isFocusableInTouchMode = false
                    et1.requestFocus()
                }
            }
        }

        findViewById<EditText>(pinValues[pinValues.size - 1]).addTextChangedListener {
            val confirmButton = findViewById<Button>(R.id.btn_confirm_pin)
            confirmButton.isEnabled = isPinCorrect()

            if (confirmButton.isEnabled) {
                confirmButton.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            } else {
                confirmButton.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled1))
                confirmButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }
    }

    private fun isPinCorrect(): Boolean {
        for (pin in pinValues) {
            if (findViewById<EditText>(pin).text.isEmpty()) {
                return false
            }
        }

        return true
    }

    private fun getCompletePin(): Int {
        var allPinValues: String = ""

        for (pin in pinValues) {
            allPinValues += findViewById<EditText>(pin).text.toString()
        }

        return allPinValues.toInt()
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_pin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}