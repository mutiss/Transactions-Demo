package com.carlosblaya.transactionsdemo.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.carlosblaya.transactionsdemo.R
import kotlin.math.absoluteValue

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.getLabel(@StringRes stringKey: Int) = this.resources.getString(stringKey)

fun Double.formatEuros(context: Context): SpannableString {
    val pending = SpannableString(
        String.format(
            context.getLabel(R.string.main_value),
            String.format("%.2f", this.absoluteValue)
        ).replace(".", ",")
    )

    val integers = pending.toString().substring(0, pending.toString().indexOf(','))
    val decimals =
        pending.toString().substring(pending.toString().indexOf(',') + 1, pending.toString().length)

    var integersFormed = ""
    if (integers.length > 3) {
        for (i in 0..(integers.length - 1)) {
            if (i == (integers.length - 3)) {
                integersFormed += "."
            }
            integersFormed += integers.get(i).toString()
        }
    }
    if (integersFormed.equals("")) {
        integersFormed = integers
    }
    var totalResult = "${integersFormed},${decimals}"
    totalResult = if (this < 0) {
        "-$totalResult"
    } else {
        totalResult
    }

    val pendingFinal = SpannableString(totalResult)
    pendingFinal.setSpan(RelativeSizeSpan(0.75f), pendingFinal.length - 4, pendingFinal.length, 0)
    return pendingFinal
}

fun TextView.colourizeAmount(context: Context) {
    if(this.text.contains("-")){
        this.setTextColor(context.resources.getColor(R.color.red,null))
    }else{
        this.setTextColor(context.resources.getColor(R.color.green,null))
    }
}

inline fun <reified T: Activity> Context.createIntent() =
    Intent(this, T::class.java)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }


