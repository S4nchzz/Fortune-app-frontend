package com.fortune.app.ui.view.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.domain.state.CardMovementState
import com.fortune.app.ui.adapters.cardMovements.MovementCardAdapter
import com.fortune.app.ui.adapters.cardMovements.MovementCardItem
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_detail)
        adjustScreenInsets()

        loadCardData()
        rViewAdapterConfig()
    }

    private fun loadCardData() {

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

                    rView.adapter = MovementCardAdapter(this, cardAdapterItems)
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