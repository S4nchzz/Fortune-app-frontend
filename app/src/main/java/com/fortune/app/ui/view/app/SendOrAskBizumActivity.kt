package com.fortune.app.ui.view.app

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.fortune.app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
import java.util.Locale

class SendOrAskBizumActivity : AppCompatActivity() {
    private var isAsking: Boolean = false
    private var totalBalance: Double = 0.0
    private var commaPressed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_send_or_ask_bizum)
        adjustScreenInsets()

        this.isAsking = intent.getBooleanExtra("ask", false)

        totalBalance = intent.getDoubleExtra("currentAmount", 0.0)
        manageButtonLogic()
    }

    private fun manageButtonLogic() {
        val buttonNumberList = arrayOf(
            R.id.zero,
            R.id.one,
            R.id.two,
            R.id.three,
            R.id.four,
            R.id.five,
            R.id.six,
            R.id.seven,
            R.id.eight,
            R.id.nine,
        )

        val phoneLayout = findViewById<TextInputLayout>(R.id.phone_layout)
        val phoneField = findViewById<EditText>(R.id.phone_field)
        val sendButton = findViewById<Button>(R.id.send_money_bizum)

        phoneField.addTextChangedListener {
            val phone = it as Editable

            if (phone.isEmpty() || phone.length < 9) {
                phoneLayout.error = "Introduzca un numero valido."
                sendButton.isEnabled = false
                return@addTextChangedListener
            }

            sendButton.isEnabled = true
        }

        val amountText = findViewById<TextView>(R.id.amount_text)
        val bizumError = findViewById<TextView>(R.id.error_bizum)

        for (id in buttonNumberList) {
            findViewById<Button>(id).setOnClickListener {
                val button = it as Button

                if (!commaPressed) {
                    if (amountText.text.length > 4 || amountText.text.toString().replace(",", ".").toDouble() > 2000) {
                        bizumError.text = "No se permiten pagos de mas de 2000€"
                        return@setOnClickListener
                    }

                    if (amountText.text.toString() == "0,00") {
                        amountText.text = button.text
                    } else {
                        amountText.text = "${amountText.text}${button.text}"
                    }
                } else {
                    if (amountText.text.toString().split(",")[1].length > 1) {
                        return@setOnClickListener
                    }


                    amountText.text = "${amountText.text}${button.text}"
                }
            }
        }

        findViewById<Button>(R.id.comma).setOnClickListener {
            if (amountText.text.toString() != "0,00" && !amountText.text.toString().contains(",")) {
                amountText.text = "${amountText.text},"
                commaPressed = true
            }
        }

        findViewById<ImageButton>(R.id.remove).setOnClickListener {
            amountText.text = "0,00"
            commaPressed = false
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