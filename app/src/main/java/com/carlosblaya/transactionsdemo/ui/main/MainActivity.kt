package com.carlosblaya.transactionsdemo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.carlosblaya.transactionsdemo.data.response.mapper.TransactionListMapper
import com.carlosblaya.transactionsdemo.databinding.ActivityMainBinding
import com.carlosblaya.transactionsdemo.ui.main.adapter.TransactionListAdapter
import com.carlosblaya.transactionsdemo.util.Status
import com.carlosblaya.transactionsdemo.util.gone
import com.carlosblaya.transactionsdemo.util.show
import com.carlosblaya.transactionsdemo.util.viewBinding
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModel()
    val binding by viewBinding(ActivityMainBinding::inflate)

    var transactionListMapper: TransactionListMapper = TransactionListMapper()

    lateinit var transactionsAdapter: TransactionListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAdapter()
        getTransactionsFromObserver()
    }

    private fun setupAdapter() {
        transactionsAdapter = TransactionListAdapter(mutableListOf())
        binding.rvTransactions.adapter = transactionsAdapter
    }

    /**
     * Get transactions list
     */
    private fun getTransactionsFromObserver() {
        lifecycleScope.launch {
            viewModel.getTransactions().observe(
                this@MainActivity,
                {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.loading.gone()
                            val transactionListJson = it.data
                            if (transactionListJson != null) {
                                if (transactionListJson.isEmpty()) {
                                    showEmptyList()
                                } else {
                                    val transactionList = transactionListMapper.toTransactionList(transactionListJson)
                                    transactionsAdapter.addAll(transactionList)
                                }
                            } else {
                                showEmptyList()
                            }
                        }
                        Status.ERROR -> {
                            showEmptyList()
                            binding.loading.gone()
                        }
                        Status.LOADING -> {
                            binding.loading.show()
                        }
                    }
                })
        }
    }

    fun showEmptyList() {
        binding.empty.show()
        binding.rvTransactions.gone()
    }
}