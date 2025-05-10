package com.fortune.app.data.entities.bank_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fortune.app.data.entities.user.UserEntity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "fl_account",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["proprietary_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "account_id")
    @SerializedName("account_id") val account_id: String,

    @ColumnInfo(name = "total_balance")
    @SerializedName("total_balance") val totalBalance: Double
)