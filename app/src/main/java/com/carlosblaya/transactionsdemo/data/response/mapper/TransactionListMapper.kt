package com.carlosblaya.transactionsdemo.data.response.mapper

import com.carlosblaya.transactionsdemo.data.response.TransactionResponse
import com.carlosblaya.transactionsdemo.domain.model.Transaction
import com.carlosblaya.transactionsdemo.util.DateFormater
import java.math.BigDecimal

class TransactionListMapper {

    /**
     * First, we map transactions list. Later, we sort by date field, and now, we distinct by id field to get most recent transaction.
     * Finally, we exclude dates with wrong format with date field set empty with previous dateformatter.
     */
    fun toTransactionList(json: List<TransactionResponse>?): List<Transaction> {
        with(json) {
            return if (this?.isNotEmpty() == true) {
                this.map {
                    toTransaction(it)
                }.sortedByDescending { transaction -> transaction.date }.distinctBy { transaction -> transaction.id }.filter { transaction -> transaction.date != "" }
            } else {
                emptyList()
            }
        }
    }

    private fun toTransaction(json: TransactionResponse): Transaction {
        with(json) {
            return Transaction(
                id = id,
                date = DateFormater.formatDateFrom(date,DateFormater.DATE_TIMEZONE,DateFormater.DAY_MONTH_YEAR_TIME),
                amount = amount,
                fee = fee,
                finalAmount = sumAmountFee(amount,fee),
                description = description
            )
        }
    }

    private fun sumAmountFee(amount:Double?, fee:Double?):Double{
        val checkFee = fee ?: 0.0
        amount.let {
            return BigDecimal(it?: 0.0).plus(BigDecimal(checkFee)).toDouble()
        }
    }
}