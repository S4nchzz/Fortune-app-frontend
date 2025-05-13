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
import com.fortune.app.domain.state.RequestedBizumState
import com.fortune.app.ui.adapters.bizums.BizumItem
import com.fortune.app.ui.adapters.cards.BizumAdapter
import com.fortune.app.ui.adapters.cards.RequestedBizumAdapter
import com.fortune.app.ui.viewmodel.bizum.Bizum_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BizumActivity : AppCompatActivity() {
    private val bizumViewmodel: Bizum_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bizum)
        adjustScreenInsets()

        bizumRView()
        requestBizumRview()
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
                            item.id,
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

    private fun requestBizumRview() {
        val requestBizumRview = findViewById<RecyclerView>(R.id.request_bizum_rview)

        bizumViewmodel.myRequestBizums.observe(this) { requestBizumState ->
            when(requestBizumState) {
                is RequestedBizumState.Success -> {
                    val rbizumItemList: MutableList<BizumItem> = mutableListOf()

                    requestBizumState.requestedBizums.forEach { item ->
                        rbizumItemList.add(BizumItem(
                            id = item.id,
                            date = item.date,
                            from = item.from,
                            amount = item.amount,
                            description = item.description,
                            amountIn = item.amountIn
                        ))
                    }

                    val dpPerItem = 120
                    val scale = this.resources.displayMetrics.density
                    val heightInPx = (dpPerItem * requestBizumState.requestedBizums.size * scale).toInt()

                    requestBizumRview.layoutParams.height = heightInPx
                    requestBizumRview.adapter = RequestedBizumAdapter(rbizumItemList)
                }

                is RequestedBizumState.Error -> {
                    // Show no request text
                }
            }
        }

        bizumViewmodel.getMyRequestBizums()
    }

    private fun manageSendOrRequestBizum() {
        findViewById<LinearLayout>(R.id.send_bizum).setOnClickListener {
            val openSend = Intent(this@BizumActivity, SendOrAskBizumActivity::class.java)
            openSend.putExtra("ask", false)
            startActivity(openSend)
        }

        findViewById<LinearLayout>(R.id.ask_bizum).setOnClickListener {
            val openSend = Intent(this@BizumActivity, SendOrAskBizumActivity::class.java)
            openSend.putExtra("ask", true)
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