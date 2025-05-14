package com.fortune.app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.ui.view.auth.LoginActivity

class AccountCreation_Dialog(val errorEncountered: Boolean, val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.custom_success_or_failure_dialog, null)

        val closeDialogToActivityButton = view.findViewById<Button>(R.id.btn_close_sof)
        val textViewMessage = view.findViewById<TextView>(R.id.dialog_message_acc_creation)
        val lottieView = view.findViewById<LottieAnimationView>(R.id.image)

        val openNextActivity: Intent?

        textViewMessage.text = message
        if (!errorEncountered) {
            closeDialogToActivityButton.text = "Login"
            lottieView.setAnimation(R.raw.checkmark_true)

            openNextActivity = Intent(context, LoginActivity::class.java)
        } else {
            closeDialogToActivityButton.text = "Volver"
            lottieView.setAnimation(R.raw.checkmark_false)

            openNextActivity = Intent(context, MainActivity::class.java)
        }

        lottieView.repeatCount = 0
        lottieView.playAnimation()

        closeDialogToActivityButton.setOnClickListener {
            startActivity(openNextActivity)
            requireActivity().finish()
        }

        builder.setView(view)

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCanceledOnTouchOutside(false)
        isCancelable = false

        return alert
    }
}