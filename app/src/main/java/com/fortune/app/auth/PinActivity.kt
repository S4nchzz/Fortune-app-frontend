package com.fortune.app.auth

import android.os.Bundle
import android.text.Editable
import android.text.method.KeyListener
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.fortune.app.R

class PinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pin)
        adjustScreenInsets()

        manageSignFocus();
    }

    private fun manageSignFocus() {
        val pinValues: List<Int> = listOf(
            R.id.firma_box_1,
            R.id.firma_box_2,
            R.id.firma_box_3,
            R.id.firma_box_4,
            R.id.firma_box_5
        )

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
            confirmButton.isEnabled = isPinCorrect(pinValues)

            if (confirmButton.isEnabled) {
                confirmButton.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            } else {
                confirmButton.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled2))
                confirmButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }
    }

    private fun isPinCorrect(pinValues: List<Int>): Boolean {
        for (pin in pinValues) {
            if (findViewById<EditText>(pin).text.isEmpty()) {
                return false
            }
        }

        return true
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_pin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}