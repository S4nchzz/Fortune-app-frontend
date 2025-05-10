package com.fortune.app.ui.view.app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortune.app.R

class BizumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bizum)
        adjustScreenInsets()

        findViewById<LinearLayout>(R.id.send_bizum).setOnClickListener {
            val openSend = Intent(this@BizumActivity, SendOrAskBizumActivity::class.java)
            openSend.putExtra("ask", false)
            openSend.putExtra("currentAmount", intent.getDoubleExtra("currentAmount", 0.0))
            startActivity(openSend)
        }

        findViewById<LinearLayout>(R.id.ask_bizum).setOnClickListener {
            val openSend = Intent(this@BizumActivity, SendOrAskBizumActivity::class.java)
            openSend.putExtra("ask", true)
            openSend.putExtra("currentAmount", intent.getDoubleExtra("currentAmount", 0.0))
            startActivity(openSend)
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