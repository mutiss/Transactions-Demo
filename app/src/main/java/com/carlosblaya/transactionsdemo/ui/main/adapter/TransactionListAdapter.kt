package com.carlosblaya.transactionsdemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.carlosblaya.transactionsdemo.R
import com.carlosblaya.transactionsdemo.TransactionsApplication
import com.carlosblaya.transactionsdemo.databinding.ItemTransactionBinding
import com.carlosblaya.transactionsdemo.databinding.ItemTransactionHeaderBinding
import com.carlosblaya.transactionsdemo.domain.model.Transaction
import com.carlosblaya.transactionsdemo.util.*
import java.util.*


class TransactionListAdapter(private val list: MutableList<Transaction>)
    : RecyclerView.Adapter<TransactionListAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return when(viewType){
            R.layout.item_transaction_header -> MainViewHolder.FirstTransactionViewHolder(
                ItemTransactionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )

            R.layout.item_transaction -> MainViewHolder.TransactionViewHolder(
                ItemTransactionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view given")
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when(holder){
            is MainViewHolder.FirstTransactionViewHolder -> holder.bind(list[position])
            is MainViewHolder.TransactionViewHolder -> holder.bind(list[position],list,position)
        }
    }

    open class MainViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        class FirstTransactionViewHolder(private val binding: ItemTransactionHeaderBinding) : MainViewHolder(binding){
            fun bind(item: Transaction){
                binding.tvDate.text = DateFormater.formatDateFrom(item.date,DateFormater.DAY_MONTH_YEAR_TIME,DateFormater.WEEKDAY_DAY_MONTH_YEAR)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                binding.tvAmount.text = item.finalAmount?.formatEuros(TransactionsApplication.context)
                binding.tvAmount.colourizeAmount(TransactionsApplication.context)
                binding.tvDescription.text = if(item.description.isNullOrBlank()) TransactionsApplication.context.resources.getString(R.string.transaction_without_description) else item.description
            }
        }

        class TransactionViewHolder(private val binding: ItemTransactionBinding) : MainViewHolder(binding){
            fun bind(item:Transaction, listTransactions:List<Transaction>, position: Int){
                binding.tvDate.text = DateFormater.formatDateFrom(item.date,DateFormater.DAY_MONTH_YEAR_TIME,DateFormater.WEEKDAY_DAY_MONTH_YEAR)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                binding.tvAmount.text = item.finalAmount?.formatEuros(TransactionsApplication.context)
                binding.tvAmount.colourizeAmount(TransactionsApplication.context)
                binding.tvDescription.text = if(item.description.isNullOrBlank()) TransactionsApplication.context.resources.getString(R.string.transaction_without_description) else item.description
                if( DateFormater.formatDateFrom(item.date,DateFormater.DAY_MONTH_YEAR_TIME,DateFormater.DAY_MONTH_YEAR) ==  DateFormater.formatDateFrom(listTransactions[position -1].date,DateFormater.DAY_MONTH_YEAR_TIME,DateFormater.DAY_MONTH_YEAR)){
                    binding.tvDate.gone()
                }else{
                    binding.tvDate.show()
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> R.layout.item_transaction_header
            else -> R.layout.item_transaction
        }
    }

    fun addAll(transactionList: List<Transaction>) {
        list.addAll(transactionList)
        notifyDataSetChanged()
    }
}