package com.fortune.app.data.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fortune.app.data.entities.user.UserEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "fl_user_profile",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class UProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "online")
    @SerializedName("online") val online: Boolean,

    @ColumnInfo(name = "user_id")
    @SerializedName("user_id") val user_id: Long,

    @ColumnInfo(name = "name")
    @SerializedName("name") val name: String,

    @ColumnInfo(name = "address")
    @SerializedName("address") val address: String,

    @ColumnInfo(name = "phone")
    @SerializedName("phone") val phone: String,

    @SerializedName("pfp") val profilePicture: String
): Serializable