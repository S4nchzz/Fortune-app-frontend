package com.fortune.app.ui.view.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.domain.state.RequestedBizumState
import com.fortune.app.ui.adapters.bizums.BizumItem
import com.fortune.app.ui.adapters.cards.RequestedBizumAdapter
import com.fortune.app.ui.viewmodel.bizum.Bizum_ViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RequestBizumActivity : AppCompatActivity() {
    private val bizumViewmodel: Bizum_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_request_bizum)
        adjustScreenInsets()

        setRview();
    }

    private fun setRview() {
        val requestBizumRview = findViewById<RecyclerView>(R.id.request_bizum_rview)

        bizumViewmodel.myRequestBizums.observe(this) { requestBizumState ->
            when(requestBizumState) {
                is RequestedBizumState.Success -> {
                    val rbizumItemList: MutableList<BizumItem> = mutableListOf()

                    requestBizumState.requestedBizums.forEach { item ->
                        rbizumItemList.add(BizumItem(
                            date = item.date,
                            from = item.from,
                            amount = item.amount,
                            description = item.description,
                            amountIn = item.amountIn
                        ))
                    }

                    requestBizumRview.adapter = RequestedBizumAdapter(rbizumItemList)
                }

                is RequestedBizumState.Error -> {

                }
            }
        }

        bizumViewmodel.getMyRequestBizums()


    }

    private fun adjustScreenInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}