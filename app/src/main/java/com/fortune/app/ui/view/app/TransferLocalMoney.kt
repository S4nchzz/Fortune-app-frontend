package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.domain.model.bank_data.CardModel
import com.fortune.app.domain.state.CardBalanceState
import com.fortune.app.domain.state.CardState
import com.fortune.app.domain.state.CardUniqueState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.ui.adapters.cards.CardItem
import com.fortune.app.ui.adapters.cards.CarouselAdapter
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferLocalMoney : AppCompatActivity() {
    private var commaPressed = false
    private var cardUUIDSelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transfer_local_money)
        adjustScreenInsets()

        findViewById<ImageView>(R.id.go_back).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        
        configRview()
        manageButtonLogic()
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

                    val adapter = CarouselAdapter(this, cardItems) { cardUUID -> cardUUIDSelected = cardUUID}
                    recyclerView.adapter = adapter

                    recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

                    val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
                    snapHelper.attachToRecyclerView(recyclerView)

                    recyclerView.post {
                        recyclerView.scrollToPosition(0)
                        val holder = recyclerView.findViewHolderForAdapterPosition(0)
                        holder?.itemView?.performClick()
                    }

                    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                            super.onScrollStateChanged(recyclerView, newState)

                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                val centerView = snapHelper.findSnapView(recyclerView.layoutManager)
                                val position = recyclerView.getChildAdapterPosition(centerView!!)

                                val holder = recyclerView.findViewHolderForAdapterPosition(position)
                                holder?.itemView?.performClick()
                            }
                        }
                    })
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

        val amountText = findViewById<TextView>(R.id.amount_text)

        val cardViewModel: Card_ViewModel by viewModels()
        val transferButton: Button = findViewById(R.id.transfer_money_btn)
        transferButton.setOnClickListener {
            cardViewModel.getCardByUUID(cardUUIDSelected)
        }

        for (id in buttonNumberList) {
            findViewById<Button>(id).setOnClickListener {
                val button = it as Button

                if (!commaPressed) {
                    if (amountText.text.length > 4 || amountText.text.toString().replace(",", ".").toDouble() > 2000) {
                        return@setOnClickListener
                    }

                    if (amountText.text.toString() == "0,00") {
                        amountText.text = button.text
                        transferButton.isEnabled = true
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
            transferButton.isEnabled = false
            commaPressed = false
        }

        cardViewModel.cardDataUUIDState.observe(this) { cardUniqueState ->
            when(cardUniqueState) {
                is CardUniqueState.Success -> {
                    val fromCardUUID = intent.getStringExtra("card_uuid").orEmpty()

                    cardViewModel.cardBalanceStateTransfer.observe(this) { cardBalanceState ->
                        when(cardBalanceState) {
                            is CardBalanceState.Success -> {
                                if (amountText.text.toString().toDouble() > cardBalanceState.card_balance) {
                                    SuccessOrFail_Dialog(true, "El importe elegido es superior al de la tarjeta que realiza la transferencia."){
                                        finish()
                                    }.show(supportFragmentManager, "Card canot be transfered")
                                    return@observe
                                }

                                cardViewModel.transferBalance.observe(this) { transferStatus ->
                                    when(transferStatus) {
                                        is DefaultState.Success -> {
                                            SuccessOrFail_Dialog(false, "Dinero transferido correctamente."){
                                                finish()
                                            }.show(supportFragmentManager, "Money transfered")
                                        }

                                        is DefaultState.Error -> {
                                            SuccessOrFail_Dialog(true, "Hubo un error al transferir el dinero."){
                                                finish()
                                            }.show(supportFragmentManager, "Money transfer error")
                                        }
                                    }
                                }

                                var ensureAmountFormat = amountText.text.toString();

                                if (ensureAmountFormat.contains(",") && ensureAmountFormat.contains(".")) {
                                    ensureAmountFormat = ensureAmountFormat.replace(".", "_");
                                    ensureAmountFormat = ensureAmountFormat.replace(",", ".");
                                    ensureAmountFormat = ensureAmountFormat.replace("_", ".");
                                } else if (ensureAmountFormat.contains(",")) {
                                    ensureAmountFormat = ensureAmountFormat.replace(",", ".")
                                }

                                cardViewModel.transferBalance(fromCardUUID, cardUUIDSelected, ensureAmountFormat.toDouble())
                            }

                            is CardBalanceState.Error -> {
                                SuccessOrFail_Dialog(true, "Ha ocurrido un error inesperado."){
                                    finish()
                                }.show(supportFragmentManager, "Unexpected error")
                            }
                        }
                    }

                    cardViewModel.cardBalanceStateTransfer(fromCardUUID)
                }

                is CardUniqueState.Error -> {
                    SuccessOrFail_Dialog(true, "Ha ocurrido un error inesperado."){
                        finish()
                    }.show(supportFragmentManager, "Unexpected error")
                }
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