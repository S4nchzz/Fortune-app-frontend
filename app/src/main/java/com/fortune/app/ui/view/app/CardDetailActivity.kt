package com.fortune.app.ui.view.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
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
import com.fortune.app.domain.state.CardMovementState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.LockCardState
import com.fortune.app.network.response.card.LockCardResponse
import com.fortune.app.ui.adapters.cardMovements.MovementCardAdapter
import com.fortune.app.ui.adapters.cardMovements.MovementCardItem
import com.fortune.app.ui.dialogs.AccountCreation_Dialog
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailActivity : AppCompatActivity() {
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
        configEventButtons()
    }

    private fun isCardBlocked() {
        val cardViewModel: Card_ViewModel by viewModels()
        cardViewModel.isCardLockedState.observe(this) { cardLockedState ->
            when(cardLockedState) {
                is LockCardState.Success -> {
                    if (cardLockedState.locked) {
                        findViewById<Button>(R.id.simulate_payment).isEnabled = false
                        val lockImage = findViewById<ImageView>(R.id.lock_status)
                        lockImage.isVisible = true
                    }
                }

                else -> {}
            }
        }


        cardViewModel.isCardLocked(intent.getStringExtra("card_uuid").toString())
    }

    private fun configEventButtons() {
        lockButton()
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
                    if (lockCardState.locked) {
                        lockImage.isVisible = true
                        simulatePayment.isEnabled = false
                        simulatePayment.setTextColor(ContextCompat.getColor(this, R.color.white))
                    } else {
                        lockImage.isVisible = false
                        simulatePayment.isEnabled = true
                        simulatePayment.setTextColor(ContextCompat.getColor(this, R.color.disabled1))
                    }
                }

                is LockCardState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un error al bloquear la tarjeta").show(supportFragmentManager, "Card locked error")
                }
            }
        }

        loadingDialog = AlertDialog.Builder(this@CardDetailActivity)
            .setView(R.layout.dialog_loading)
            .create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.setCancelable(false)

        findViewById<ImageButton>(R.id.lock_card).setOnClickListener {
            loadingDialog.show()

            cardViewModel.lockCard(intent.getStringExtra("card_uuid").toString())
        }
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