package com.fortune.app.network.response.bizum

import java.util.Date

data class MyBizumsResponse(val date: Date, val from: String, val amount: Double, val description: String, val amountIn: Boolean)
