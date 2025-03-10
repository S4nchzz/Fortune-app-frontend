package com.fortune.app.ui.view.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.ui.dialogs.IncorrectCredentials_Dialog
import com.fortune.app.ui.view.MainAppActivity
import com.fortune.app.ui.viewmodel.uiStates.LoginResponseState
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        adjustScreenInsets()

        loadingDialog = AlertDialog.Builder(this@LoginActivity)
            .setView(R.layout.loading_dialog)
            .create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.setCancelable(false)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginServerRequest()
        }
    }

    private fun loginServerRequest() {
        val identity_document: String = findViewById<EditText>(R.id.login_data_dniNie).text.toString()
        val password: String = findViewById<EditText>(R.id.login_data_password).text.toString()

        loadingDialog.show()

        val credentialFailDialog = IncorrectCredentials_Dialog(this)

        val authViewModel: User_ViewModel by viewModels()
        authViewModel.login.observe(this) { loginState ->
            if (loginState != null) {
                loadingDialog.dismiss()

                when (loginState) {
                    is LoginResponseState.Error -> {
                        authViewModel.resetLoginObserve()
                        credentialFailDialog.show(supportFragmentManager, "Incorrect credentials")
                    }

                    is LoginResponseState.Success -> {
                        val openMainAppActivity = Intent(this@LoginActivity, MainAppActivity::class.java)
                        startActivity(openMainAppActivity)
                        finish()
                    }
                }
            }
        }

        authViewModel.login(identity_document, password)
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}