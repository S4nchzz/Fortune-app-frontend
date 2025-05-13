package com.fortune.app.data.config.api.bizum

import com.fortune.app.network.request.bizum.DenyBizumRequest
import com.fortune.app.network.request.bizum.MakeBizumRequest
import com.fortune.app.network.response.bizum.BizumsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BizumAPIRest {
    @POST("/b_operations/bizum/makeBizum")
    suspend fun makeBizum(@Header("Authorization") token: String, @Body bizumRequest: MakeBizumRequest): Response<Unit>

    @POST("/b_operations/bizum/getBizums")
    suspend fun getBizums(@Header("Authorization") token: String): Response<List<BizumsResponse>>

    @POST("/b_operations/bizum/requestBizum")
    suspend fun requestBizum(@Header("Authorization") token: String, @Body makeBizumRequest: MakeBizumRequest): Response<Unit>

    @POST("/b_operations/bizum/getRequestedBizums")
    suspend fun getRequestBizums(@Header("Authorization") token: String): Response<List<BizumsResponse>>

    @POST("/b_operations/bizum/denyBizumRequest")
    suspend fun denyBizumRequest(@Header("Authorization") token: String, @Body denyBizumRequest: DenyBizumRequest): Response<Unit>
}