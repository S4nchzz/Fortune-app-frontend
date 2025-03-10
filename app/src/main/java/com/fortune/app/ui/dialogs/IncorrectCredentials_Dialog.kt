package com.fortune.app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.fortune.app.R

class IncorrectCredentials_Dialog constructor(
    context: Context
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_incorrect_credentials, null)

        view.findViewById<Button>(R.id.btn_credential_close_dialog).setOnClickListener {
            this.dismiss()
        }

        builder.setView(view)

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCancelable(false)

        return alert;
    }
}