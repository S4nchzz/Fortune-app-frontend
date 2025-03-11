package com.fortune.app.domain.model.bank_data

data class AccountModel(
    val id: Int = 0,
    val accountId: String,
    val proprietaryId: Long,
    val totalBalance: Double
)