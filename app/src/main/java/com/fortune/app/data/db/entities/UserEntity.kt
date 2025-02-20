package com.fortune.app.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fl_user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("user_id") val id: Long,

    @ColumnInfo(name = "identity_document")
    @SerializedName("identityDocument") val identity_document: String,

    @ColumnInfo(name = "email")
    @SerializedName("email") val email: String,

    @ColumnInfo(name = "digital_sign")
    @SerializedName("digital_sign") val digitalSign: Int?,

    @ColumnInfo(name = "is_profile_created")
    @SerializedName("is_profile_created") var isProfileCreated: Boolean
)