package com.carlosblaya.transactionsdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @Expose @SerializedName("id") val id: Long?,
    @Expose @SerializedName("date") val date: String?,
    @Expose @SerializedName("amount") val amount: Double?,
    @Expose @SerializedName("fee") val fee: Double?,
    @Expose @SerializedName("description") val description: String?
)