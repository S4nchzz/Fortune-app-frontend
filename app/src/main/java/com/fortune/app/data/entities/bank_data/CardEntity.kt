package com.fortune.app.data.entities.bank_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fl_card_data")
data class CardEntity(
    @SerializedName("cardID") val cardID: Int,

    @ColumnInfo(name = "card_type")
    @SerializedName("card_type") val cardType: String,

    @ColumnInfo(name = "card_number")
    @SerializedName("card_number") val card_number: String,

    @ColumnInfo(name = "exp_date")
    @SerializedName("exp_date") val expDate: String,

    @ColumnInfo(name = "balance")
    @SerializedName("balance") val balance: Double,

    @ColumnInfo(name = "blocked")
    @SerializedName("blocked") val blocked: Boolean
)
