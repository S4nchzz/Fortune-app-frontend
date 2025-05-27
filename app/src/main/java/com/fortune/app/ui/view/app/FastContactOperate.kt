package com.fortune.app.ui.view.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R
import com.fortune.app.domain.state.UProfileState
import com.fortune.app.ui.dialogs.SuccessOrFail_Dialog
import com.fortune.app.ui.viewmodel.user.User_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FastContactOperate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fast_contact_operate)
        adjustScreenInsets()

        setupBasicInfo()
        manageActionButtons()
    }

    private fun setupBasicInfo() {
        val userViewmodel: User_ViewModel by viewModels()
        userViewmodel.fastContactProfile.observe(this) { fastContactProfile ->
            when (fastContactProfile) {
                is UProfileState.Success -> {
                    val base64String = fastContactProfile.uProfileModel.pfp
                    if (base64String.isNotBlank()) {
                        try {
                            val cleanBase64 = if (base64String.contains(",")) {
                                base64String.substringAfter(",")
                            } else base64String

                            val decodedBytes = android.util.Base64.decode(cleanBase64, android.util.Base64.DEFAULT)
                            val bitmap = android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

                            findViewById<ImageView>(R.id.fast_contact_pfp).setImageBitmap(bitmap)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            findViewById<ImageView>(R.id.fast_contact_pfp).setImageResource(R.drawable.profile_default_profile_icon)
                        }
                    } else {
                        findViewById<ImageView>(R.id.fast_contact_pfp).setImageResource(R.drawable.profile_default_profile_icon)
                    }

                    findViewById<TextView>(R.id.fast_contact_name).text = fastContactProfile.uProfileModel.name
                }

                is UProfileState.Error -> {
                    SuccessOrFail_Dialog(true, "Ha ocurrido un error al intentar cargar el perfil del usuario"){
                        finish()
                    }.show(supportFragmentManager, "Error on fastContactProfile")
                }
            }
        }

        userViewmodel.getProfileFastContact(intent.getLongExtra("to_id", -1))
    }

    private fun manageActionButtons() {
        val openBizumActivity = Intent(this@FastContactOperate, SendOrAskBizumActivity::class.java)
        openBizumActivity.putExtra("fast_operation", true)
        openBizumActivity.putExtra("to_id", intent.getLongExtra("to_id", -1))

        findViewById<LinearLayout>(R.id.send_bizum).setOnClickListener {
            openBizumActivity.putExtra("ask", false)
            startActivity(openBizumActivity)
        }

        findViewById<LinearLayout>(R.id.ask_bizum).setOnClickListener {
            openBizumActivity.putExtra("ask", true)
            startActivity(openBizumActivity)
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