package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.domain.state.AccountBalanceState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import com.fortune.app.ui.viewmodel.bizum.Bizum_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BizumRequestAcceptOrDeny : AppCompatActivity() {
    private val bizumViewmodel: Bizum_ViewModel by viewModels()
    private val accountViewmodel: Account_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bizum_request_accept_or_deny)
        adjustScreenInsets()

        findViewById<ImageView>(R.id.go_back).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        
        val bizumID = intent.getIntExtra("bizumID", 0)
        val date = intent.getStringExtra("date")
        val from = intent.getStringExtra("from")
        val description = intent.getStringExtra("description")
        val amount = intent.getDoubleExtra("amount", 0.0)

        loadBizumData(date, from, description, amount)

        manageAcceptButton(bizumID, amount)
        manageDenyButton(bizumID)
    }

    private fun loadBizumData(date: String?, from: String?, description: String?, amount: Double) {
        findViewById<TextView>(R.id.request_final_bizum_date).text = date
        findViewById<TextView>(R.id.request_final_bizum_from).text = from
        findViewById<TextView>(R.id.request_final_bizum_description).text = description
        findViewById<TextView>(R.id.request_final_bizum_amount).text = "${amount} €"
    }

    private fun manageAcceptButton(bizumID: Int, bizumAmount: Double) {
        bizumViewmodel.acceptBizum.observe(this) { acceptState ->
            when(acceptState) {
                is DefaultState.Success -> {
                    SuccessOrFail_Dialog(false, "Se ha enviado el pago correspondiente."){
                        this@BizumRequestAcceptOrDeny.finish()
                    }.show(supportFragmentManager, "Bizum request accept")
                }

                is DefaultState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un problema al realizar la peticion"){
                        this@BizumRequestAcceptOrDeny.finish()
                    }.show(supportFragmentManager, "Bizum request accept error")
                }
            }
        }

        accountViewmodel.accountBizumBalanceState.observe(this) { accountBalanceState ->
            when(accountBalanceState) {
                is AccountBalanceState.Success -> {
                    if (accountBalanceState.accountBalanceResponse.accountBalance >= bizumAmount) {
                        bizumViewmodel.acceptBizum(bizumID)
                    } else {
                        SuccessOrFail_Dialog(true, "No dispone del balance necesario para realizar esta operacion."){
                            this@BizumRequestAcceptOrDeny.finish()
                        }.show(supportFragmentManager, "Operation balance")
                    }
                }

                is AccountBalanceState.Error -> {
                    SuccessOrFail_Dialog(true, "Ha ocurrido un error inesperado."){
                        this@BizumRequestAcceptOrDeny.finish()
                    }.show(supportFragmentManager, "Operation error")
                    findViewById<Button>(R.id.btn_accept).isEnabled = true
                    findViewById<Button>(R.id.btn_deny).isEnabled = true
                }
            }
        }

        findViewById<Button>(R.id.btn_accept).setOnClickListener{
            findViewById<Button>(R.id.btn_accept).isEnabled = false
            findViewById<Button>(R.id.btn_deny).isEnabled = false

            accountViewmodel.getAccountBalanceBizum()
        }
    }

    private fun manageDenyButton(bizumID: Int) {
        bizumViewmodel.denyBizum.observe(this) { deleteState ->
            when(deleteState) {
                is DefaultState.Success -> {
                    SuccessOrFail_Dialog(false, "Se ha denegado la peticion") {
                        this@BizumRequestAcceptOrDeny.finish()
                    }.show(supportFragmentManager, "Bizum request denied")
                }

                is DefaultState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un problema al denegar la peticion"){
                        this@BizumRequestAcceptOrDeny.finish()
                    }.show(supportFragmentManager, "Bizum request denied error")
                }
            }
        }

        findViewById<Button>(R.id.btn_deny).setOnClickListener {
            bizumViewmodel.denyBizum(bizumID)
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