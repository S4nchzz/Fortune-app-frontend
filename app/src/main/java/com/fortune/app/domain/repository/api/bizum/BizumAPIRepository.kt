package com.fortune.app.domain.repository.api.bizum

import com.fortune.app.domain.state.MakeBizumState

interface BizumAPIRepository {
    suspend fun makeBizum(s: String, amount: Double, phone: String, description: String): MakeBizumState
    suspend fun getBizums(s: String): Any
}