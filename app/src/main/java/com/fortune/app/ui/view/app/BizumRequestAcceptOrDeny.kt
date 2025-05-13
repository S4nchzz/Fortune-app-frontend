package com.fortune.app.ui.view.app

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R

class BizumRequestAcceptOrDeny : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bizum_request_accept_or_deny)
        adjustScreenInsets()

        val bizumID = intent.getIntExtra("bizumID", 0)

        manageAcceptButton()
        manageDenyButton()
    }

    private fun manageAcceptButton() {

    }

    private fun manageDenyButton() {

    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}