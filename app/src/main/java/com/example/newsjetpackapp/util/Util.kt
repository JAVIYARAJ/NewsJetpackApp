package com.example.newsjetpackapp.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class Util {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDateAndTime(date: String):String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat=SimpleDateFormat("MMMM dd, yyyy",Locale.getDefault())
            val inputDate=inputFormat.parse(date)
            return outputFormat.format(inputDate)
        }
    }

}