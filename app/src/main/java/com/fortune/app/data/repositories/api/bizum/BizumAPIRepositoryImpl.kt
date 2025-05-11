package com.fortune.app.data.repositories.api.bizum

import com.fortune.app.data.config.api.bizum.BizumAPIRest
import com.fortune.app.domain.repository.api.bizum.BizumAPIRepository
import com.fortune.app.domain.state.BizumState
import com.fortune.app.domain.state.MyBizumsState
import com.fortune.app.network.request.bizum.MakeBizumRequest
import com.fortune.app.network.response.bizum.MyBizumsResponse
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
                val bizumList: MutableList<MyBizumsResponse> = mutableListOf()

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
}