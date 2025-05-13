package com.fortune.app.ui.adapters.bizums

import java.util.Date

data class BizumItem(val id: Int, val date: Date, val from: String, val amount: Double, val description: String, val amountIn: Boolean)
