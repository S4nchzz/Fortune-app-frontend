package com.fortune.app.data.repositories.api.bizum

import com.fortune.app.data.config.api.bizum.BizumAPIRest
import com.fortune.app.domain.repository.api.bizum.BizumAPIRepository
import com.fortune.app.domain.state.BizumState
import com.fortune.app.domain.state.DefaultState
import com.fortune.app.domain.state.MyBizumsState
import com.fortune.app.domain.state.RequestedBizumState
import com.fortune.app.network.request.bizum.BizumIDRequest
import com.fortune.app.network.request.bizum.MakeBizumRequest
import com.fortune.app.network.response.bizum.BizumsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class BizumAPIRepositoryImpl @Inject constructor(
    val retrofit: Retrofit
) : BizumAPIRepository {
    private val bizumAPIService = retrofit.create(BizumAPIRest::class.java)

    override suspend fun makeBizum(token: String, amount: Double, phone: String, description: String): BizumState {
        return withContext(Dispatchers.IO) {
            val response = bizumAPIService.makeBizum(token, MakeBizumRequest(amount, phone, description))

            if (response.code() == 200) {
                BizumState.Success
            } else if (response.code() == 404) {
                BizumState.UserNotFound
            } else {
                BizumState.Error
            }
        }
    }

    override suspend fun getBizums(token: String): MyBizumsState {
        return withContext(Dispatchers.IO) {
            val response = bizumAPIService.getBizums(token)

            if (response.code() == 200 && response.body() != null) {
                val bizumList: MutableList<BizumsResponse> = mutableListOf()

                response.body()!!.forEach { myBizumsResponse ->
                    bizumList.add(myBizumsResponse)
                }

                MyBizumsState.Success(bizumList)
            } else {
                MyBizumsState.Error
            }
        }
    }

    override suspend fun requestBizum(token: String, amount: Double, phone: String, description: String): BizumState {
        return withContext(Dispatchers.IO) {
            val response = bizumAPIService.requestBizum(token, MakeBizumRequest(amount, phone, description))

            if (response.code() == 200) {
                BizumState.Success
            } else if (response.code() == 404) {
                BizumState.UserNotFound
            } else {
                BizumState.Error
            }
        }
    }

    override suspend fun getRequestBizums(token: String): RequestedBizumState {
        return withContext(Dispatchers.IO) {
            val response = bizumAPIService.getRequestBizums(token)

            if (response.code() == 200 && response.body() != null) {
                val requestBizumList: MutableList<BizumsResponse> = mutableListOf()
                response.body()!!.forEach { item -> requestBizumList.add(item) }

                RequestedBizumState.Success(requestBizumList)
            } else {
                RequestedBizumState.Error
            }
        }
    }

    override suspend fun denyBizumRequest(token: String, bizumID: Int): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = bizumAPIService.denyBizumRequest(token, BizumIDRequest(bizumID))

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }

    override suspend fun acceptBizumRequest(token: String, bizumID: Int): DefaultState {
        return withContext(Dispatchers.IO) {
            val response = bizumAPIService.acceptBizumRequest(token, BizumIDRequest(bizumID))

            if (response.code() == 200) {
                DefaultState.Success
            } else {
                DefaultState.Error
            }
        }
    }
}