package com.fortune.app.ui.view.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
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
import com.fortune.app.domain.state.AccountBalanceState
import com.fortune.app.domain.state.BizumState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import com.fortune.app.ui.viewmodel.bizum.Bizum_ViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendOrAskBizumActivity : AppCompatActivity() {
    private var isAsking: Boolean = false
    private var commaPressed: Boolean = false
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_send_or_ask_bizum)
        adjustScreenInsets()

        this.isAsking = intent.getBooleanExtra("ask", false)

        getCurrentBalance{ accountBalance ->
            manageButtonLogic(accountBalance)
        }
    }

    private fun manageButtonLogic(currentBalance: Double) {
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
            } else if (phone.length == 9) {
                phoneLayout.error = ""
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

        val bizumViewModel: Bizum_ViewModel by viewModels()

        bizumViewModel.makeBizum.observe(this) { makeBizumState ->
            when(makeBizumState) {
                is BizumState.Success -> {
                    SuccessOrFail_Dialog(false, "El bizum se ha enviado correctamente al destinatario.").show(supportFragmentManager, "Bizum sended")
                }

                is BizumState.UserNotFound -> {
                    SuccessOrFail_Dialog(true, "No se ha encontrado el destinatario, por favor, compruebe el numero de telefono").show(supportFragmentManager, "Bizum not sended")
                }

                is BizumState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un error al procesar la solicitud.").show(supportFragmentManager, "Bizum error")
                }
            }

            loadingDialog.dismiss()
        }

        bizumViewModel.requestBizum.observe(this) { makeBizumState ->
            when(makeBizumState) {
                is BizumState.Success -> {
                    SuccessOrFail_Dialog(false, "Se ha solicitado el bizum al destinatario.").show(supportFragmentManager, "Bizum sended")
                }

                is BizumState.UserNotFound -> {
                    SuccessOrFail_Dialog(true, "No se ha encontrado el destinatario, por favor, compruebe el numero de telefono").show(supportFragmentManager, "Bizum not sended")
                }

                is BizumState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un error al procesar la solicitud.").show(supportFragmentManager, "Bizum error")
                }
            }

            loadingDialog.dismiss()
        }

        sendButton.setOnClickListener {
            var ensureAmountFormat = amountText.text.toString();
            phoneLayout.error = ""
            bizumError.text = ""

            if (ensureAmountFormat.contains(",") && ensureAmountFormat.contains(".")) {
                ensureAmountFormat = ensureAmountFormat.replace(".", "_");
                ensureAmountFormat = ensureAmountFormat.replace(",", ".");
                ensureAmountFormat = ensureAmountFormat.replace("_", ".");
            } else if (ensureAmountFormat.contains(",")) {
                ensureAmountFormat = ensureAmountFormat.replace(",", ".")
            }

            val contidion1: Boolean = ensureAmountFormat.toDouble() < 2000
            val contidion2: Boolean = phoneField.text.toString().length == 9 && (phoneField.text.startsWith("6") || phoneField.text.startsWith("7"))

            val condition3: Boolean = currentBalance >= ensureAmountFormat.toDouble()

            if (contidion1 && contidion2 && (condition3 || isAsking)) {
                loadDialog()
                val description = findViewById<EditText>(R.id.description_field)

                if (!isAsking) {
                    bizumViewModel.makeBizum(ensureAmountFormat.toDouble(), phoneField.text.toString(), description.text.toString());
                } else {
                    bizumViewModel.requestBizum(ensureAmountFormat.toDouble(), phoneField.text.toString(), description.text.toString())
                }
                return@setOnClickListener
            }

            if (!contidion1) {
                bizumError.text = "La cantidad enviada no puede superar los 2000 €."
                return@setOnClickListener
            }

            if (!contidion2) {
                bizumError.text = "Asegurese de introducir un numero de telefono valido"
                return@setOnClickListener
            }

            /**
             * Fake warning, this warning is happening
             * because the compiler cannot analyze
             * that an api response can return a new
             * value, hes choosing 0,0 by default probably
             */
            if (!condition3 && !isAsking) {
                bizumError.text = "No dispone de la cantidad elegida."
                return@setOnClickListener
            }
        }
    }

    private fun getCurrentBalance(callback: (Double) -> Unit) {
        val accountViewModel: Account_ViewModel by viewModels()

        loadDialog()
        accountViewModel.accountBalanceState.observe(this) { accountBalanceState ->
            when(accountBalanceState) {
                is AccountBalanceState.Success -> {
                    loadingDialog.dismiss()
                    callback(accountBalanceState.accountBalanceResponse.accountBalance)
                }

                is AccountBalanceState.Error -> {
                    loadingDialog.dismiss()
                    callback(0.0)
                }
            }
        }


        accountViewModel.getAccountBalance()
    }

    private fun loadDialog() {
        loadingDialog = AlertDialog.Builder(this@SendOrAskBizumActivity)
            .setView(R.layout.dialog_loading)
            .create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}