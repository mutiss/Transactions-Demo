package com.carlosblaya.transactionsdemo.data.network.services

import com.carlosblaya.transactionsdemo.data.response.TransactionResponse
import retrofit2.http.GET

interface TransactionsApiInterface {

    @GET("transactions.json")
    suspend fun getTransactions(): List<TransactionResponse>

}