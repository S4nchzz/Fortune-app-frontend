package com.fortune.app.domain.state

import com.fortune.app.network.response.bizum.BizumsResponse

sealed class MyBizumsState() {
    data class Success(var bizumList: List<BizumsResponse> = arrayListOf()): MyBizumsState()
    object Error: MyBizumsState()
}
