package com.carlosblaya.transactionsdemo.domain.repository

import com.carlosblaya.transactionsdemo.data.network.ResponseHandler
import com.carlosblaya.transactionsdemo.data.network.services.TransactionsApiInterface
import com.carlosblaya.transactionsdemo.data.response.TransactionResponse
import com.carlosblaya.transactionsdemo.util.Resource

class TransactionsRepository(
    private val transactionsApiInterface: TransactionsApiInterface,
    private val responseHandler: ResponseHandler
){
    suspend fun getTransactions(): Resource<List<TransactionResponse>> {
        return try {
            val response = transactionsApiInterface.getTransactions()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}