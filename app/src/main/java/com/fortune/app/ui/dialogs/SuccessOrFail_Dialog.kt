package com.fortune.app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.fortune.app.R

class SuccessOrFail_Dialog(val errorEncountered: Boolean, val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.custom_success_or_failure_dialog, null)

        val closeDialogToActivityButton = view.findViewById<Button>(R.id.btn_login_after_acc_creation)
        val textViewMessage = view.findViewById<TextView>(R.id.dialog_message_acc_creation)
        val lottieView = view.findViewById<LottieAnimationView>(R.id.image)

        closeDialogToActivityButton.text = "Cerrar"
        textViewMessage.text = message
        if (!errorEncountered) {
            lottieView.setAnimation(R.raw.checkmark_true)
        } else {
            lottieView.setAnimation(R.raw.checkmark_false)
        }

        lottieView.repeatCount = 0
        lottieView.playAnimation()

        closeDialogToActivityButton.setOnClickListener {
            this.dismiss()
        }

        builder.setView(view)

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCanceledOnTouchOutside(false)
        isCancelable = false

        return alert
    }
}