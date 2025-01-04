package com.application.x_pass.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtils {

    companion object {

        /**
         * Parses a date string into a Date object using the provided format.
         *
         * @param dateString The date string to parse.
         * @param format The expected format of the date string. Defaults to ISO 8601.
         * @return The parsed Date object.
         * @throws IllegalArgumentException If the date string cannot be parsed.
         */
        fun parseDateString(dateString: String, format: String = "yyyy-MM-dd'T'HH:mm:ss"): Date {
            return try {
                val dateFormat = SimpleDateFormat(format, Locale.getDefault())
                dateFormat.parse(dateString)
                    ?: throw IllegalArgumentException("Unable to parse date")
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid date format: $dateString", e)
            }
        }

        /**
         * Formats a Date object into a string using the provided format.
         *
         * @param date The Date object to format.
         * @param format The desired string format. Defaults to a readable format.
         * @return A formatted date string.
         */
        fun formatDateString(date: Date, format: String = "dd MMM yyyy, HH:mm"): String {
            return try {
                val dateFormat = SimpleDateFormat(format, Locale.getDefault())
                dateFormat.format(date)
            } catch (e: Exception) {
                "Invalid Date"
            }
        }
        fun formatDateHistoryString(date: Date, format: String = "dd.MMM.yyyy | HH:mm"): String {
            return try {
                val dateFormat = SimpleDateFormat(format, Locale.getDefault())
                dateFormat.format(date)
            } catch (e: Exception) {
                "Invalid Date"
            }
        }

        /**
         * Applies a time shift to a Date object.
         *
         * @param date The Date object to modify.
         * @param timeShiftMinutes The number of minutes to shift the time by.
         * @return A new Date object with the applied time shift.
         */
        fun applyTimeShift(date: Date?, timeShift: Double): Date? {
            if (date == null) {
                return null
            }

            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(
                Calendar.MINUTE,
                (timeShift * 60).toInt()
            )

            return calendar.time
        }
    }


}
