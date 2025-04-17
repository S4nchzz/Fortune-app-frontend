package com.fortune.app.ui.view.user

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.data.entities.user.dto.UProfileDTO
import com.fortune.app.data.entities.user.dto.UserDTO
import com.fortune.app.domain.state.RegisterState
import com.fortune.app.ui.dialogs.AccountSuccessfullyCreated_Dialog
import com.fortune.app.ui.viewmodel.auth.Auth_ViewModel
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
            handleFullRegister(name.text.toString(), address.text.toString(), phone.text.toString())
        }
    }

    private fun handleFullRegister(name: String, address: String, phone: String) {
        val authViewmodel: Auth_ViewModel by viewModels()

        if (checkData(name, address, phone)) {
            val uProfileDTO = UProfileDTO(name, address, phone)
            val userDTO = intent.getSerializableExtra("userDTO", UserDTO::class.java)

            loadingDialog = AlertDialog.Builder(this@UProfileActivity)
                .setView(R.layout.dialog_loading)
                .create()
            loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog.setCancelable(false)
            loadingDialog.show()

            authViewmodel.register.observe(this) { registerState ->
                loadingDialog.dismiss()

                when(registerState) {
                    is RegisterState.Success -> {
                        AccountSuccessfullyCreated_Dialog(false, "Se ha creado la cuenta correctamente. por favor, inicie sesion para finalizar la configuracion de la cuenta").show(supportFragmentManager, "Account successfully created")
                    }

                    is RegisterState.UserAlreadyExistsError -> {
                        AccountSuccessfullyCreated_Dialog(true, "El usuario ya existe, por favor, intentelo de nuevo.").show(supportFragmentManager, "Account already exists")
                    }

                    is RegisterState.UnexpectedError -> {
                        AccountSuccessfullyCreated_Dialog(true, "Ha ocurrido un error inesperado").show(supportFragmentManager, "Unexpected error")
                    }
                }
            }

            authViewmodel.register(userDTO, uProfileDTO)
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