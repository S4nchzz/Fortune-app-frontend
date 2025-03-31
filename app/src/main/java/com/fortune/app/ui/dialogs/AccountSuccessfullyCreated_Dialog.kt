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
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.ui.view.auth.LoginActivity

class AccountSuccessfullyCreated_Dialog(val errorEncountered: Boolean, val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_success_account_creation, null)

        val closeDialogToActivityButton = view.findViewById<Button>(R.id.btn_login_after_acc_creation)
        val textViewMessage = view.findViewById<TextView>(R.id.dialog_message_acc_creation)
        val image = view.findViewById<ImageView>(R.id.dialog_image_acc_creation)

        val openNextActivity: Intent?

        if (!errorEncountered) {
            closeDialogToActivityButton.text = "Login"
            textViewMessage.text = message
            image.setImageDrawable(requireActivity().getDrawable(R.drawable.icon_checkmark_true))

            openNextActivity = Intent(context, LoginActivity::class.java)
        } else {
            closeDialogToActivityButton.text = "Volver"
            textViewMessage.text = message
            image.setImageDrawable(requireActivity().getDrawable(R.drawable.icon_checkmark_false))

            openNextActivity = Intent(context, MainActivity::class.java)
        }

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