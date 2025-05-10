package com.fortune.app.domain.state

import com.fortune.app.network.response.bizum.MyBizumsResponse
import java.util.Date

sealed class MyBizumsState() {
    data class Success(var bizumList: List<MyBizumsResponse> = arrayListOf()): MyBizumsState()
    object Error: MyBizumsState()
}
