package com.carlosblaya.transactionsdemo.domain.model

data class Transaction(
    val id: Long?,
    val date:String?,
    val amount: Double?,
    val fee: Double? = 0.0,
    val finalAmount: Double?,
    val description: String?
)