package com.fortune.app.data.config.api.bizum

import com.fortune.app.network.request.bizum.MakeBizumRequest
import com.fortune.app.network.response.bizum.MyBizumsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BizumAPIRest {
    @POST("/b_operations/bizum/makeBizum")
    suspend fun makeBizum(@Header("Authorization") token: String, @Body bizumRequest: MakeBizumRequest): Response<Unit>

    @POST("/b_operations/bizum/getBizums")
    suspend fun getBizums(token: String): Response<List<MyBizumsResponse>>
}