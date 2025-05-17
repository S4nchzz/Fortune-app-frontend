package com.fortune.app.ui.view.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.state.CardState
import com.fortune.app.ui.adapters.cards.CardAdapter
import com.fortune.app.ui.adapters.cards.CardItem
import com.fortune.app.ui.adapters.cards.CarouselAdapter
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferLocalMoney : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transfer_local_money)
        adjustScreenInsets()

        configRview()
    }

    private fun configRview() {
        val userViewmodel: User_ViewModel by viewModels()

        userViewmodel.transferCards.observe(this) { cardState ->
            when(cardState) {
                is CardState.Success -> {
                    val cardItems: MutableList<CardItem> = mutableListOf()
                    cardState.cards.forEach { cardModel: CardModel ->
                        cardItems.add(
                            CardItem(cardModel.uuid, cardModel.cardType, cardModel.cardNumber.takeLast(4).toInt(), cardModel.balance)
                        )
                    }

                    val recyclerView = findViewById<RecyclerView>(R.id.carouselRecyclerView)
                    val adapter = CarouselAdapter(this, cardItems)
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

                    val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
                    snapHelper.attachToRecyclerView(recyclerView)
                }

                is CardState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un problema al obtener las tarjetas"){
                        finish()
                    }.show(supportFragmentManager, "Transfer card get fail")
                }
            }
        }

        userViewmodel.transferCards()
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}