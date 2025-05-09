package com.fortune.app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.fortune.app.R
import com.fortune.app.domain.state.PaymentSimulationState
import com.fortune.app.ui.viewmodel.bank_data.Account_ViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimulatePayment_Dialog(val currentCardAmount: Double, val cardUUID: String, val callback: (paymentSimulated: Boolean) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_simulate_payment, null)

        builder.setView(view)

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCanceledOnTouchOutside(false)

        val receptorEntityLayout = view.findViewById<TextInputLayout>(R.id.receptor_entity_layout)
        val receptorEntityField = view.findViewById<EditText>(R.id.receptor_entity_field)

        val amountLayout = view.findViewById<TextInputLayout>(R.id.amount_layout)
        val amountField = view.findViewById<EditText>(R.id.amount_field)

        val accountViewModel: Account_ViewModel by viewModels()

        view.findViewById<Button>(R.id.btn_pay).setOnClickListener {
            if (currentCardAmount <= 0.0) {
                amountLayout.error = "Introduzca un numero valido."
                return@setOnClickListener
            }

            if (currentCardAmount < amountField.text.toString().toDouble()) {
                amountLayout.error = "No dispone de esta cantidad."
                return@setOnClickListener
            }

            if (amountField.text.isNotEmpty() && receptorEntityField.text.isNotEmpty()) {
                accountViewModel.simulatePaymentState.observe(this) { simulatePaymentState ->
                    when(simulatePaymentState) {
                        is PaymentSimulationState.Success -> {
                            callback(true)
                        }

                        is PaymentSimulationState.Error -> {
                            callback(false)
                        }
                    }
                }

                accountViewModel.simulatePayment(cardUUID, amountField.text.toString().toDouble(), receptorEntityField.text.toString())
            } else {
                amountLayout.error = "Este campo no puede estar vacio."
                receptorEntityLayout.error = "Este campo no puede estar vacio."
            }

            this.dismiss()
        }

        return alert
    }
}