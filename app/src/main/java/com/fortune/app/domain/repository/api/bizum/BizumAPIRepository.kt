package com.fortune.app.domain.repository.api.bizum

import com.fortune.app.domain.state.BizumState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.MyBizumsState
import com.fortune.app.domain.state.RequestedBizumState

interface BizumAPIRepository {
    suspend fun makeBizum(token: String, amount: Double, phone: String, description: String): BizumState
    suspend fun getBizums(token: String): MyBizumsState
    suspend fun requestBizum(
        token: String,
        amount: Double,
        phone: String,
        description: String
    ): BizumState

    suspend fun getRequestBizums(s: String): RequestedBizumState
    suspend fun denyBizumRequest(s: String, bizumID: Int): DefaultState
    suspend fun acceptBizumRequest(s: String, bizumID: Int): DefaultState
}