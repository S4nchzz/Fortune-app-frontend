package com.fortune.app.domain.state

import com.fortune.app.domain.model.bizum.BizumModel
import com.fortune.app.network.response.bizum.BizumsResponse

sealed class RequestedBizumState {
    data class Success(val requestedBizums: List<BizumsResponse>):RequestedBizumState()
    object Error: RequestedBizumState()
}
