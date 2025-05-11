package com.fortune.app.ui.view.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
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
    }

    private fun manageSecurityButton() {
        val navigationView = findViewById<NavigationView>(R.id.security_navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.add_card -> {
                    loadDialog()
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

                    cardViewmodel.addCard()
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

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}