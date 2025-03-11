package com.fortune.app.domain.model.user

data class UProfileModel(
    val id: Int = 0,
    val online: Boolean,
    val userId: Long,
    val name: String,
    val address: String,
    val phone: String)