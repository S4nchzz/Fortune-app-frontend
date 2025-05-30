package com.fortune.app.ui.view.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.MainActivity
import com.fortune.app.R
import com.fortune.app.domain.state.MyBizumsState
import com.fortune.app.domain.state.RequestedBizumState
import com.fortune.app.ui.adapters.bizums.BizumItem
import com.fortune.app.ui.adapters.cards.BizumAdapter
import com.fortune.app.ui.adapters.cards.RequestedBizumAdapter
import com.fortune.app.ui.view.MainAppActivity
import com.fortune.app.ui.viewmodel.bizum.Bizum_ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BizumActivity : AppCompatActivity() {
    private val bizumViewmodel: Bizum_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bizum)
        adjustScreenInsets()

        findViewById<ImageView>(R.id.go_back).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        bizumRView()
        requestBizumRview()
        manageSendOrRequestBizum()
        manageBottomNavigation()
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

    private fun manageBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.bizum_bm

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bizum_bm -> {
                    true
                }
                R.id.home_bm -> {
                    val openMain = Intent(this@BizumActivity, MainAppActivity::class.java)
                    startActivity(openMain)
                    true
                }
                R.id.security_bm -> {
                    val openSec = Intent(this@BizumActivity, SecurityActivity::class.java)
                    startActivity(openSec)
                    true
                }
                R.id.logout_bm -> {
                    val openDefault = Intent(this@BizumActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(openDefault)
                    true
                }
                else -> false
            }
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