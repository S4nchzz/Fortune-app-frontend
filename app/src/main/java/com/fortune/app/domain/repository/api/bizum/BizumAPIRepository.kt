package com.fortune.app.domain.repository.api.bizum

interface BizumAPIRepository {
    suspend fun makeBizum(s: String, amount: Double, phone: String): Any
}