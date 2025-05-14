package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.domain.state.AccountDataState
import com.fortune.app.domain.state.MovementState
import com.fortune.app.ui.adapters.movements.MovementAdapter
import com.fortune.app.ui.adapters.movements.MovementItem
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class AccountActivity : AppCompatActivity() {
    private val accountViewmodel: Account_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        adjustScreenInsets()

        requestAccountData()
        loadRViewData()
    }

    private fun loadRViewData() {
        accountViewmodel.accountMovement.observe(this) { movementAccountState ->
            when(movementAccountState) {
                is MovementState.Success -> {
                    val movementItems: MutableList<MovementItem> = mutableListOf()

                    movementAccountState.movementModel.forEach { item ->
                        movementItems.add(
                            MovementItem(
                                mDate = item.date,
                                mReceiver = item.entityReceiver,
                                mAmount = item.amount
                            )
                        )
                    }

                    findViewById<RecyclerView>(R.id.account_rview).adapter = MovementAdapter(movementItems)
                }

                is MovementState.Error -> {

                }
            }

        }

        accountViewmodel.getAccountMovements()
    }

    private fun requestAccountData() {
        accountViewmodel.accountDataState.observe(this) { accountDataState ->
            when(accountDataState) {
                is AccountDataState.Success -> {
                    findViewById<TextView>(R.id.account_id).text = accountDataState.accountID

                    val formattedBalance = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
                        .format(accountDataState.accountBalance)
                        .replace("€", " €")
                    findViewById<TextView>(R.id.account_amount).text = formattedBalance
                }

                is AccountDataState.Error -> {
                    SuccessOrFail_Dialog(true, "No se ha podido recuperar los datos de la cuenta"){
                        finish()
                    }.show(supportFragmentManager, "Account data error")
                }
            }
        }

        accountViewmodel.getAccountData()
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}