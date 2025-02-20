package com.fortune.app.ui.view.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.ui.viewmodel.user.UProfile_ViewModel
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        adjustScreenInsets()

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginServerRequest()
        }
    }

    private fun loginServerRequest() {
        val identity_document: String = findViewById<EditText>(R.id.login_data_dniNie).text.toString()
        val password: String = findViewById<EditText>(R.id.login_data_password).text.toString()

        val alertDialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_dialog)
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.show()

        val authViewModel: User_ViewModel by viewModels()
        authViewModel.login.observe(this) { userEntity ->
            alertDialog.dismiss()

            if (userEntity.digitalSign == null && !userEntity.isProfileCreated) {
                val openProfileView = Intent(this@LoginActivity, UProfileActivity::class.java)
                startActivity(openProfileView)
            } else if (userEntity.digitalSign == null) {
                val uProfileViewModel: UProfile_ViewModel by viewModels()

                // Ensure that when the user already has a profile save it in the database
                uProfileViewModel.profile.observe(this) {
                    val openPinActivity = Intent(this@LoginActivity, PinActivity::class.java)
                    startActivity(openPinActivity)
                }

                uProfileViewModel.migrateProfileFromAPI(userEntity.id)

            } else {
                // Open main view
            }
        }

        clearUserDataDBData() // Clear data before add new one
        authViewModel.login(identity_document, password)
    }

    private fun clearUserDataDBData() {
        val userViewModel: User_ViewModel by viewModels()
        userViewModel.clearLocalUsers()
    }
    
    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}