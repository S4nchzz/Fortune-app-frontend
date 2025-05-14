package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.domain.state.AccountDataState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : AppCompatActivity() {
    private val accountViewmodel: Account_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        adjustScreenInsets()

        requestAccountData()
    }

    private fun requestAccountData() {
        accountViewmodel.accountDataState.observe(this) { accountDataState ->
            when(accountDataState) {
                is AccountDataState.Success -> {
                    findViewById<TextView>(R.id.account_id).text = accountDataState.accountID
                    findViewById<TextView>(R.id.account_amount).text = accountDataState.accountBalance.toString()
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