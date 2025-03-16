package com.fortune.app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.fortune.app.R
import com.fortune.app.ui.view.auth.LoginActivity

class AccountSuccesfullyCreated_Dialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_success_account_creation, null)

        view.findViewById<Button>(R.id.btn_login_after_acc_creation).setOnClickListener {
            val openLoginActivity = Intent(context, LoginActivity::class.java)
            startActivity(openLoginActivity)
            requireActivity().finish()
        }

        builder.setView(view)

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)

        return alert
    }
}