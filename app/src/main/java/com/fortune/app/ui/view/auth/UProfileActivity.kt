package com.fortune.app.ui.view.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.domain.model.user.UProfileModel
import com.fortune.app.ui.dialogs.AccountSuccesfullyCreated_Dialog
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import com.fortune.app.ui.viewmodel.user.Auth_ViewModel
import com.fortune.app.ui.viewmodel.user.UProfile_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UProfileActivity : AppCompatActivity() {
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)
        adjustScreenInsets()

        findViewById<ImageButton>(R.id.back_event_arrow).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val name: EditText = findViewById(R.id.profile_name_data)
        val address: EditText = findViewById(R.id.profile_address_data)
        val phone: EditText = findViewById(R.id.profile_phone_data)

        findViewById<Button?>(R.id.btn_confirm_profile).setOnClickListener {
            handleGenerateProfile(name.text.toString(), address.text.toString(), phone.text.toString())
        }
    }

    val authViewmodel: Auth_ViewModel by viewModels()
    val uProfileViewModel: UProfile_ViewModel by viewModels()
    private fun handleGenerateProfile(name: String, address: String, phone: String) {
        if (checkData(name, address, phone)) {
            val uProfileDTO = UProfileDTO(name, address, phone)
            val userDTO = intent.getSerializableExtra("userDTO", UserDTO::class.java)

            loadingDialog = AlertDialog.Builder(this@UProfileActivity)
                .setView(R.layout.dialog_loading)
                .create()
            loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog.setCancelable(false)
            loadingDialog.show()

            authViewmodel.register.observe(this) {
                uProfileViewModel.profile.observe(this) {
                    loadingDialog.dismiss()
                    AccountSuccesfullyCreated_Dialog().show(supportFragmentManager, "Account succesfully created")
                }

                uProfileViewModel.createProfile(uProfileDTO)
            }

            authViewmodel.register(userDTO)
        }
    }

    private fun checkData(name: String, address: String, phone: String): Boolean {
        return !(name.isEmpty() && address.isEmpty() && phone.isEmpty())
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}