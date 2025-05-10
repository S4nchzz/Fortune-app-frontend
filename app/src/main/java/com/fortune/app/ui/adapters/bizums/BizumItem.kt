package com.fortune.app.ui.adapters.bizums

import java.util.Date

data class BizumItem(val date: Date, val to: String, val amount: Double, val description: String, val amountIn: Boolean)
