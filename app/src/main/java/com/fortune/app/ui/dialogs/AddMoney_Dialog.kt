package com.fortune.app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.domain.state.NewBalanceState
import com.fortune.app.ui.view.auth.LoginActivity
import com.fortune.app.ui.viewmodel.bank_data.Card_ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMoney_Dialog(val callback: (balanceAdded: Boolean, newAmount: Double) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_money, null)

        builder.setView(view)

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCanceledOnTouchOutside(false)

        val amountSelectedField = view.findViewById<EditText>(R.id.add_balance_amount)
        val amountSelectedLayout = view.findViewById<TextInputLayout>(R.id.add_balance_amount_layout)

        val cardViewModel: Card_ViewModel by viewModels()

        view.findViewById<Button>(R.id.btn_add_balance).setOnClickListener {
            if (amountSelectedField.text.isNotEmpty()) {
                cardViewModel.addBalanceState.observe(this) { addBalanceState ->
                    when(addBalanceState) {
                        is NewBalanceState.Success -> {
                            callback(true, amountSelectedField.text.toString().toDouble())
                            this.dismiss()
                        }

                        is NewBalanceState.Error -> {
                            callback(false, amountSelectedField.text.toString().toDouble())
                            this.dismiss()
                        }
                    }
                }

                cardViewModel.addBalance(amountSelectedField.text.toString().toDouble())
            } else {
                amountSelectedLayout.error = "El campo no puede estar vacio."
            }
        }

        return alert
    }
}