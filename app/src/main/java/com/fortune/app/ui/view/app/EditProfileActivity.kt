package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.domain.state.ProfileToUpdateState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        adjustScreenInsets()

        configureFields()
        manageSendData()
    }

    private fun manageSendData() {
        val userViewmodel: User_ViewModel by viewModels()

        val update = findViewById<Button>(R.id.btn_update_profile).setOnClickListener {
            val name = findViewById<EditText>(R.id.nameField).text
            val address = findViewById<EditText>(R.id.addressField).text
            val identityDocument = findViewById<EditText>(R.id.identityDocumentField).text
            val email = findViewById<EditText>(R.id.emailField).text
            val phone = findViewById<EditText>(R.id.phoneField).text

            userViewmodel.updateProfile(name.toString(), address.toString(), identityDocument.toString(), email.toString(), phone.toString())
        }
    }

    private fun configureFields() {
        val userViewmodel: User_ViewModel by viewModels()
        userViewmodel.profileToUpdate.observe(this) { profileToUpdateState ->
            when(profileToUpdateState) {
                is ProfileToUpdateState.Success -> {
                    findViewById<EditText>(R.id.nameField).setText(profileToUpdateState.profileToUpdateResponse.name)
                    findViewById<EditText>(R.id.addressField).setText(profileToUpdateState.profileToUpdateResponse.address)
                    findViewById<EditText>(R.id.identityDocumentField).setText(profileToUpdateState.profileToUpdateResponse.identityDocument)
                    findViewById<EditText>(R.id.emailField).setText(profileToUpdateState.profileToUpdateResponse.email)
                    findViewById<EditText>(R.id.phoneField).setText(profileToUpdateState.profileToUpdateResponse.phone)
                }

                is ProfileToUpdateState.Error -> {
                    SuccessOrFail_Dialog(true, "No se ha podido recuperar el perfil."){
                        finish()
                    }.show(supportFragmentManager, "Update profile data error")
                }
            }
        }

        userViewmodel.getProfileToUpdate()
    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}