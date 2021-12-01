package com.carlosblaya.transactionsdemo.domain.usecases

import com.carlosblaya.transactionsdemo.data.response.TransactionResponse
import com.carlosblaya.transactionsdemo.domain.repository.TransactionsRepository
import com.carlosblaya.transactionsdemo.util.Resource

class GetTransactionsUseCase(private val transactionsRepository: TransactionsRepository){
    suspend fun getTransactions(): Resource<List<TransactionResponse>> {
        return transactionsRepository.getTransactions()
    }
}