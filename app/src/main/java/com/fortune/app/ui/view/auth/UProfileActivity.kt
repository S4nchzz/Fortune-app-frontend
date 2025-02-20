package com.fortune.app.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.ui.viewmodel.user.UProfile_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)
        adjustScreenInsets()

        val name: EditText = findViewById(R.id.profile_name_data)
        val address: EditText = findViewById(R.id.profile_address_data)
        val phone: EditText = findViewById(R.id.profile_phone_data)

        findViewById<Button?>(R.id.btn_confirm_profile).setOnClickListener {
            handleGenerateProfile(name.text.toString(), address.text.toString(), phone.text.toString())
        }
    }

    private fun handleGenerateProfile(name: String, address: String, phone: String) {
        if (checkData(name, address, phone)) {
            val uProfileViewModel: UProfile_ViewModel by viewModels()

            uProfileViewModel.profile.observe(this) { uProfileEntity ->
                if (uProfileEntity != null) {
                    // Load main app
                    Toast.makeText(this@UProfileActivity, uProfileEntity.name, Toast.LENGTH_SHORT).show()
                    val openPinActivity = Intent(this@UProfileActivity, PinActivity::class.java)
                    startActivity(openPinActivity)
                }
            }

            uProfileViewModel.createProfile(name, address, phone)
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