package com.alvarengadev.movieschallenge.utils.format

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object FormatText {

    private val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun getLongDate(date: String?): String? {
        val day = date?.let { formatDate.parse(it) }
        return day?.let { DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH).format(it) }
    }

    fun getMediumDate(date: String?): String? {
        val day = date?.let { formatDate.parse(it) }
        return day?.let {
            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH).format(day)
        }
    }

    fun getShortDate(date: String?): String? {
        val day = date?.let { formatDate.parse(it) }
        return day?.let { DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH).format(day) }
    }
}