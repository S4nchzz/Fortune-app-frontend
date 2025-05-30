package com.fortune.app.ui.view.app

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.view.MainAppActivity
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecurityActivity : AppCompatActivity() {
    private val cardViewmodel: Card_ViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_security)
        adjustScreenInsets()
        findViewById<ImageView>(R.id.go_back).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        manageSecurityButton()
        manageBottomNavigation()
    }

    private fun manageSecurityButton() {
        val navigationView = findViewById<NavigationView>(R.id.security_navigation_view)
        cardViewmodel.addNewCard.observe(this) { defaultState ->
            when(defaultState) {
                is DefaultState.Success -> {
                    SuccessOrFail_Dialog(false, "Se ha creado la tarjeta correctamente.").show(supportFragmentManager, "Card creation")
                    loadingDialog.dismiss()
                }

                is DefaultState.Error -> {
                    SuccessOrFail_Dialog(true, "Hubo un problema al crear la tarjeta.").show(supportFragmentManager, "Card creation")
                    loadingDialog.dismiss()
                }
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.add_card -> {
                    loadDialog()
                    cardViewmodel.addCard()
                    true
                }

                R.id.reset_password -> {
                    val openResetPassword = Intent(this@SecurityActivity, ChangePasswordActivity::class.java)
                    startActivity(openResetPassword)
                    true
                }

                R.id.change_digital_sign -> {
                    val openChangeDigitalSignActivity = Intent(this@SecurityActivity, ChangeDigitalSignActivity::class.java)
                    startActivity(openChangeDigitalSignActivity)
                    true
                }

                else -> false
            }
        }
    }

    private fun loadDialog() {
        loadingDialog = AlertDialog.Builder(this@SecurityActivity)
            .setView(R.layout.dialog_loading)
            .create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun manageBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.security_bm

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bizum_bm -> {
                    val openBizum = Intent(this@SecurityActivity, BizumActivity::class.java)
                    startActivity(openBizum)
                    true
                }
                R.id.home_bm -> {
                    val openMain = Intent(this@SecurityActivity, MainAppActivity::class.java)
                    startActivity(openMain)
                    true
                }
                R.id.security_bm -> {
                    true
                }
                R.id.logout_bm -> {
                    val openDefault = Intent(this@SecurityActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(openDefault)
                    true
                }
                else -> false
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