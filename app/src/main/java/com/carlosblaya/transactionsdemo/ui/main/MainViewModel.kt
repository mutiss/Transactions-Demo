package com.carlosblaya.transactionsdemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.carlosblaya.transactionsdemo.data.response.TransactionResponse
import com.carlosblaya.transactionsdemo.domain.usecases.GetTransactionsUseCase
import com.carlosblaya.transactionsdemo.ui.base.BaseViewModel
import com.carlosblaya.transactionsdemo.util.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(
    private val transactionsUseCase: GetTransactionsUseCase
) : BaseViewModel() {

    fun getTransactions(): LiveData<Resource<List<TransactionResponse>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(transactionsUseCase.getTransactions())
        }
}