package com.fortune.app.ui.view.app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.domain.state.MyBizumsState
import com.fortune.app.network.response.bizum.MyBizumsResponse
import com.fortune.app.ui.adapters.bizums.BizumItem
import com.fortune.app.ui.adapters.cards.BizumAdapter
import com.fortune.app.ui.viewmodel.bizum.Bizum_ViewModel

class BizumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bizum)
        adjustScreenInsets()

        bizumRView()
        manageSendOrRequestBizum()
    }

    private fun bizumRView() {
        val bizumViewmodel: Bizum_ViewModel by viewModels()

        val rView = findViewById<RecyclerView>(R.id.recent_bizums_rview)

        bizumViewmodel.myBizums.observe(this) { myBizumState ->
            when(myBizumState) {
                is MyBizumsState.Success -> {
                    val bizumItems: MutableList<BizumItem> = mutableListOf()

                    myBizumState.bizumList.forEach { item ->
                        bizumItems.add(BizumItem(
                            item.date,
                            item.from,
                            item.amount,
                            item.description,
                            item.amountIn
                        ))
                    }

                    rView.adapter = BizumAdapter(bizumItems)
                }

                is MyBizumsState.Error -> {}
            }
        }


        bizumViewmodel.getMyBizums()

    }

    private fun manageSendOrRequestBizum() {
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