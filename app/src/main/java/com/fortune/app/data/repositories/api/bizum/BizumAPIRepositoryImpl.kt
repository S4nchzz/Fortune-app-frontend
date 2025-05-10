package com.fortune.app.data.repositories.api.bizum

import com.fortune.app.data.config.api.bank_data.AccountAPIRest
import com.fortune.app.data.config.api.bizum.BizumAPIRest
import com.fortune.app.domain.repository.api.bizum.BizumAPIRepository
import com.fortune.app.domain.state.MakeBizumState
import com.fortune.app.network.request.bizum.MakeBizumRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class BizumAPIRepositoryImpl @Inject constructor(
    val retrofit: Retrofit
) : BizumAPIRepository {
    private val bizumAPIService = retrofit.create(BizumAPIRest::class.java)

    override suspend fun makeBizum(token: String, amount: Double, phone: String): MakeBizumState {
        return withContext(Dispatchers.IO) {
            val response = bizumAPIService.makeBizum(token, MakeBizumRequest(amount, phone))

            if (response.code() == 200) {
                MakeBizumState.Success
            } else if (response.code() == 404) {
                MakeBizumState.UserNotFound
            } else {
                MakeBizumState.Error
            }
        }
    }
}