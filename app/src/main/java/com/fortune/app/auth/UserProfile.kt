package com.fortune.app.auth

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)
        adjustScreenInsets()

        if (checkData()) {
            // open pin
        }
    }

    private fun checkData(): Boolean {
        val name: EditText = findViewById(R.id.profile_name_data)
        val address: EditText = findViewById(R.id.profile_address_data)
        val telf: EditText = findViewById(R.id.profile_telf_data)

        if (name.text.isEmpty() || address.text.isEmpty() || telf.text.isEmpty()) {
            return false
        }

        return true
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}