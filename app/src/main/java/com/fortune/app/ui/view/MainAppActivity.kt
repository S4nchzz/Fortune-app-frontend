package com.fortune.app.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.state.AccountState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.UProfileState
import com.fortune.app.ui.adapters.cards.CardAdapter
import com.fortune.app.ui.adapters.cards.CardItem
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_app)
        adjustScreenInsets()

        loadUserViewData()
    }

    private fun loadUserViewData() {
        val userViewModel: User_ViewModel by viewModels()

        getProfile(userViewModel)
        getAccount(userViewModel)
        getCards(userViewModel)
    }

    private fun getCards(userViewModel: User_ViewModel) {
        userViewModel.cards.observe(this) { cardState ->
            when(cardState) {
                is CardState.Success -> {
                    val cardItems: MutableList<CardItem> = mutableListOf()
                    cardState.cards.forEach { cardModel: CardModel ->
                        cardItems.add(
                            CardItem(cardModel.cardType, cardModel.cardNumber.takeLast(4).toInt(), cardModel.balance)
                        )
                    }

                    findViewById<RecyclerView>(R.id.card_recycler_view).adapter = CardAdapter(cardItems)
                }

                is CardState.Error -> {

                }
            }
        }

        userViewModel.getCards()
    }

    private fun getAccount(userViewModel: User_ViewModel) {
        userViewModel.account.observe(this) { accountState ->
            when(accountState) {
                is AccountState.Success -> {
                    findViewById<TextView>(R.id.account_total_balance).text = "${accountState.accountModel.totalBalance} €"
                }

                is AccountState.Error -> {
                    val openMain = Intent(this@MainAppActivity, MainActivity::class.java)
                    startActivity(openMain)
                    finish()
                }
            }
        }

        userViewModel.getAccount()
    }

    private fun getProfile(userViewModel: User_ViewModel) {
        userViewModel.profile.observe(this) { userProfileState ->
            when(userProfileState) {
                is UProfileState.Success -> {
                    findViewById<TextView>(R.id.client_name).text = userProfileState.uProfileModel.name
                }

                is UProfileState.Error -> {
                    val openMain = Intent(this@MainAppActivity, MainActivity::class.java)
                    startActivity(openMain)
                    finish()
                }
            }
        }

        userViewModel.getProfile()
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_main_app)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}