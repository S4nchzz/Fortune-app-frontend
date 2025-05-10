package com.fortune.app.network.request.bizum

data class MakeBizumRequest(val amount: Double, val phone: String, val description: String)
