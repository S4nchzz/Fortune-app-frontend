package com.fortune.app.data.entities.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "fl_user")
data class UserEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") val id: Long,

    @ColumnInfo(name = "identity_document")
    @SerializedName("identity_document") val identity_document: String,

    @ColumnInfo(name = "email")
    @SerializedName("email") val email: String,

    @ColumnInfo(name = "digital_sign")
    @SerializedName("digital_sign") val digitalSign: Int?,

    @ColumnInfo(name = "is_profile_created")
    @SerializedName("is_profile_created") var isProfileCreated: Boolean,
)