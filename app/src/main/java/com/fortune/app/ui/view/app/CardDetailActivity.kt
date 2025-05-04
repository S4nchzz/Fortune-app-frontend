package com.fortune.app.ui.view.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.domain.state.CardBalanceState
import com.fortune.app.domain.state.CardCvvState
import com.fortune.app.domain.state.CardExpDateState
import com.fortune.app.domain.state.CardMovementState
import com.fortune.app.domain.state.CardNumberState
import com.fortune.app.domain.state.LockCardState
import com.fortune.app.ui.adapters.cardMovements.MovementCardAdapter
import com.fortune.app.ui.adapters.cardMovements.MovementCardItem
import com.fortune.app.ui.dialogs.SignOperation_Dialog
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailActivity : AppCompatActivity() {
    private var cardNumberHided: Boolean = true
    private var cvvHided: Boolean = true
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_detail)
        adjustScreenInsets()

        loadCardData()
        rViewAdapterConfig()
    }

    private fun loadCardData() {
        isCardBlocked()
        getExpDate()
        getBalance()
        getCardNumber { number ->
            findViewById<TextView>(R.id.card_number).text = getHintCard(number)
            cardNumberHided = true
        }
        configEventButtons()
    }

    private fun configEventButtons() {
        viewCardNumber()
        lockButton()
        getCvvButton()
    }

    private fun viewCardNumber() {
        findViewById<ImageButton>(R.id.view_card).setOnClickListener {
            getCardNumber { number ->
                if (cardNumberHided) {
                    findViewById<TextView>(R.id.card_number).text = number.chunked(4).joinToString(" ")
                    (it as ImageView).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.eye_opened))
                } else {
                    findViewById<TextView>(R.id.card_number).text = getHintCard(number)
                    (it as ImageView).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.closed_eye))
                }
                cardNumberHided = !cardNumberHided
            }
        }
    }

    private fun getHintCard(number: String): String {
        return number.dropLast(4).chunked(4).joinToString(" ") { "****" } +
                " " + number.takeLast(4)
    }

    private fun getCardNumber(number: (String) -> Unit) {
        val cardViewModel: Card_ViewModel by viewModels()
        cardViewModel.cardNumber.observe(this) { cardNumberState ->
            when(cardNumberState) {
                is CardNumberState.Success -> {
                    number(cardNumberState.number)
                }

                is CardNumberState.Error -> {
                    // Show dialog info with error
                }
            }
        }

        cardViewModel.getCardNumber(intent.getStringExtra("card_uuid").toString())
    }

    private fun lockButton() {
        val cardViewModel: Card_ViewModel by viewModels()

        cardViewModel.lockCardState.observe(this) { lockCardState ->
            loadingDialog.dismiss()
            when(lockCardState) {
                is LockCardState.Success -> {
                    val appendLockedOrUnlockedMessage: String = if (lockCardState.locked) "bloqueado" else "desbloqueado"
                    SuccessOrFail_Dialog(false, "Se ha ${appendLockedOrUnlockedMessage} la tarjeta").show(supportFragmentManager, "Card locked or unlocked")

                    val lockImage = findViewById<ImageView>(R.id.lock_status)
                    val simulatePayment = findViewById<Button>(R.id.simulate_payment)
                    val lockButton = findViewById<ImageButton>(R.id.lock_card)

                    if (lockCardState.locked) {
                        lockImage.isVisible = true
                        simulatePayment.isEnabled = false
                        simulatePayment.setTextColor(ContextCompat.getColor(this, R.color.disabled1))
                        lockButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lock_unlocked))
                    } else {
                        lockImage.isVisible = false
                        simulatePayment.isEnabled = true
                        simulatePayment.setTextColor(ContextCompat.getColor(this, R.color.white))
                        lockButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lock))
                    }
                }

                is LockCardState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un error al bloquear la tarjeta").show(supportFragmentManager, "Card locked error")
                }
            }
        }

        findViewById<ImageButton>(R.id.lock_card).setOnClickListener {
            SignOperation_Dialog { isPinCorrect ->
                if (isPinCorrect) {
                    loadingDialog = AlertDialog.Builder(this@CardDetailActivity)
                        .setView(R.layout.dialog_loading)
                        .create()
                    loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    loadingDialog.setCancelable(false)
                    loadingDialog.show()
                    cardViewModel.lockCard(intent.getStringExtra("card_uuid").toString())
                } else {
                    SuccessOrFail_Dialog(true, "La firma digital es incorrecta").show(supportFragmentManager, "Sign operation status")
                }
            }.show(supportFragmentManager, "Manage Digital sign auth")
        }
    }

    private fun getExpDate() {
        val cardViewModel: Card_ViewModel by viewModels()

        cardViewModel.cardExpDate.observe(this) { cardExpDateState ->
            when(cardExpDateState) {
                is CardExpDateState.Success -> {
                    val formattedDate = cardExpDateState.cardExpDate.split("/").let {
                        val month = it[0].padStart(2, '0')
                        val year = it[1].takeLast(2)
                        "$month/$year"
                    }

                    findViewById<TextView>(R.id.exp_date).text = formattedDate
                }

                is CardExpDateState.Error -> {
                    // Show dialog with error
                }
            }
        }

        cardViewModel.getExpDate(intent.getStringExtra("card_uuid").toString())
    }

    private fun getBalance() {
        val cardViewModel: Card_ViewModel by viewModels()
        cardViewModel.cardBalanceState.observe(this) { cardBalanceState ->
            when(cardBalanceState) {
                is CardBalanceState.Success -> {
                    var formattedBalance = "%,.2f".format(cardBalanceState.card_balance)

                    findViewById<TextView>(R.id.card_balance).text = "${formattedBalance} €"
                }

                is CardBalanceState.Error -> {

                }
            }
        }

        cardViewModel.getBalance(intent.getStringExtra("card_uuid").toString())
    }

    private fun getCvvButton() {
        val cardViewModel: Card_ViewModel by viewModels()

        cardViewModel.cardCvvState.observe(this) { cardCvvState ->
            when(cardCvvState) {
                is CardCvvState.Success -> {
                    val cvvField = findViewById<TextView>(R.id.card_cvv)
                    cvvField.isVisible = true
                    cvvField.text = "CVV: ${cardCvvState.cardCvv}"
                }

                is CardCvvState.Error -> {

                }
            }
        }

        findViewById<ImageButton>(R.id.view_cvv).setOnClickListener {
            if (cvvHided) {
                cardViewModel.getCvv(intent.getStringExtra("card_uuid").toString())
            } else {
                findViewById<TextView>(R.id.card_cvv).isVisible = false
            }
            cvvHided = !cvvHided
        }
    }

    private fun isCardBlocked() {
        val cardViewModel: Card_ViewModel by viewModels()
        cardViewModel.isCardLockedState.observe(this) { cardLockedState ->
            when(cardLockedState) {
                is LockCardState.Success -> {
                    if (cardLockedState.locked) {
                        findViewById<MaterialButton>(R.id.simulate_payment).isEnabled = false
                        val lockImage = findViewById<ImageView>(R.id.lock_status)
                        lockImage.isVisible = true
                    }
                }

                else -> {}
            }
        }


        cardViewModel.isCardLocked(intent.getStringExtra("card_uuid").toString())
    }

    private fun rViewAdapterConfig() {
        val cardViewModel: Card_ViewModel by viewModels()
        val rView = findViewById<RecyclerView>(R.id.card_activity_rview)

        cardViewModel.movementState.observe(this) { cardMovementState ->
            when(cardMovementState) {
                is CardMovementState.Success -> {
                    val cardAdapterItems: MutableList<MovementCardItem> = mutableListOf()

                    cardMovementState.cardMovementModel.forEach { cardMovement ->
                        cardAdapterItems.add(MovementCardItem(cardMovement.date, cardMovement.entityReceiver, cardMovement.amount))
                    }

                    rView.adapter = MovementCardAdapter(cardAdapterItems)
                }

                is CardMovementState.Error -> {
                    // show dialog with error finding data
                }
            }
        }

        cardViewModel.findCardMovements(intent.getStringExtra("card_uuid").toString())
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}