package com.fortune.app.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.fortune.app.R
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.ui.viewmodel.auth.Auth_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignOperation_Dialog(private val callback: (isPinCorrect: Boolean) -> Unit) : DialogFragment() {
    private val pinValues: List<Int> = listOf(
        R.id.dialog_firma_box_1,
        R.id.dialog_firma_box_2,
        R.id.dialog_firma_box_3,
        R.id.dialog_firma_box_4,
        R.id.dialog_firma_box_5
    )
    private val authViewmodel: Auth_ViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_pin, null)

        manageSignFocus(view)

        view.findViewById<Button>(R.id.sign_operation).setOnClickListener {
            if (isPinCorrect(view)) {

                authViewmodel.signOperation.observe(this) { signOperationState ->
                    when(signOperationState) {
                        is DefaultState.Success -> {
                            callback(true)
                        }

                        is DefaultState.Error -> {
                            callback(false)
                        }
                    }

                    this.dismiss()
                }

                authViewmodel.signOperation(getCompletePin(view))
            }
        }

        builder.setView(view)

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCanceledOnTouchOutside(false)

        return alert;
    }

    private fun manageSignFocus(view: View) {
        for (i in 0 until pinValues.size - 1) {
            val et1 = view.findViewById<EditText>(pinValues[i])
            val et2 = view.findViewById<EditText>(pinValues[i + 1])

            et1.addTextChangedListener {
                if (it?.length == 1) {
                    et1.isFocusable = false
                    et1.isFocusableInTouchMode = false

                    et2.isFocusable = true
                    et2.isFocusableInTouchMode = true
                    et2.requestFocus()
                }
            }

            et2.addTextChangedListener {
                if (it?.length == 0) {
                    et1.isFocusable = true
                    et1.isFocusableInTouchMode = true

                    et2.isFocusable = false
                    et2.isFocusableInTouchMode = false
                    et1.requestFocus()
                }
            }
        }

        view.findViewById<EditText>(pinValues[pinValues.size - 1]).addTextChangedListener {
            val confirmButton = view.findViewById<Button>(R.id.sign_operation)
            confirmButton.isEnabled = isPinCorrect(view)

            if (confirmButton.isEnabled) {
                confirmButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                confirmButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.disabled1))
                confirmButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun isPinCorrect(view: View): Boolean {
        for (pin in pinValues) {
            if (view.findViewById<EditText>(pin).text.isEmpty()) {
                return false
            }
        }

        return true
    }

    private fun getCompletePin(view: View): Int {
        var allPinValues = ""

        for (pin in pinValues) {
            allPinValues += view.findViewById<EditText>(pin).text.toString()
        }

        return allPinValues.toInt()
    }
}