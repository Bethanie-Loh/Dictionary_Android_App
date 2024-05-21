package com.example.dictionary.utils

import android.icu.util.Calendar

class Date {
    private val calendar: Calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH) + 1
    private val day = calendar.get(Calendar.DAY_OF_MONTH)
    private var formattedDate = ""

    fun getCurrentDate(): String {
        formattedDate = "$day/$month/$year"
        return formattedDate
    }
}