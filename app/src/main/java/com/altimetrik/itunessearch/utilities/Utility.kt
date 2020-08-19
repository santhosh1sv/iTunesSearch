package com.altimetrik.itunessearch.utilities

import android.annotation.SuppressLint
import com.altimetrik.itunessearch.models.Result
import java.text.SimpleDateFormat
import java.util.*

class Utility {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun convertDate(text: String, originalFormat: String, targetFormat: String): String {
            try {
                val original = SimpleDateFormat(originalFormat, Locale.US)
                val target = SimpleDateFormat(targetFormat)
                val date = original.parse(text)!!
                val formattedDate: String = target.format(date)
                return formattedDate
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }


    }
}