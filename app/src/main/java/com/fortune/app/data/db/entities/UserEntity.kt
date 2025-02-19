package com.fortune.app.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fl_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "user_id")
    @SerializedName("user_id") val user_id: Long,

    @ColumnInfo(name = "identity_document")
    @SerializedName("identityDocument") val identity_document: String,

    @ColumnInfo(name = "email")
    @SerializedName("email") val email: String,

    @ColumnInfo(name = "digital_sign")
    @SerializedName("digital_sign") val digitalSign: Int?
)