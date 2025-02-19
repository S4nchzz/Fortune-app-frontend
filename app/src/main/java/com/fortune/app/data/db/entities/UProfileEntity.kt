package com.fortune.app.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "fl_user_profile",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "online")
    @SerializedName("online") val online: Boolean,

    @ColumnInfo(name = "user_id")
    @SerializedName("user_id") val userId: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name") val name: String,

    @ColumnInfo(name = "address")
    @SerializedName("address") val address: String,

    @ColumnInfo(name = "phone")
    @SerializedName("phone") val phone: String,

    //@ColumnInfo(name = "pfp")
    //@SerializedName("pfp") val profilePicture: ByteArray
)