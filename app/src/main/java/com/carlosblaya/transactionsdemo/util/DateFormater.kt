package com.carlosblaya.transactionsdemo.util

import java.text.SimpleDateFormat
import java.util.*

class DateFormater {
    companion object {
        const val DATE_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DAY_MONTH_YEAR_TIME = "dd/MM/yyyy HH:mm:ss"
        const val DAY_MONTH_YEAR = "dd/MM/yyyy"
        const val WEEKDAY_DAY_MONTH_YEAR = "EEEE dd/MM/yyyy"
        fun formatDateFrom(stringDate: String?, formatFrom: String, formatTo: String): String {
            return try {
                val formatterTo = SimpleDateFormat(formatTo, Locale.getDefault())
                val formatterFrom = SimpleDateFormat(formatFrom, Locale.getDefault())
                formatterTo.format(formatterFrom.parse(stringDate))
            } catch (e: Exception) {
                ""
            }
        }
    }
}