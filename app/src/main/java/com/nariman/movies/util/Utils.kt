package com.nariman.movies.util

import java.text.NumberFormat
import java.util.*

class Utils {
    companion object {

        // Makes string with Quotation marks
        @JvmStatic
        fun stringWithQuotation(str: String?): String? {
            if (str != null) return "\" " + str + " \""
            return str
        }

        // Makes Money Format string
        @JvmStatic
        fun stringCurrencyFormat(number: Long?): String {

            if (number == null || number == 0L) return "not available"
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
            numberFormat.maximumFractionDigits = 0
            numberFormat.currency = Currency.getInstance(Locale.US)
            return numberFormat.format(number)
        }
    }
}

