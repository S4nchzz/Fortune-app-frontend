package com.fortune.app.domain.model.user

data class UserModel (val id: Long, val identityDocument: String, val email: String, val digitalSign: Int?, var is_profile_created: Boolean)