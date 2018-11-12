package com.matheusvillela.tmdbupcomingmovies.util

import androidx.room.TypeConverter

class ListOfStringsConverter {
    @TypeConverter
    fun listFromString(stringsString: String): List<String> {
        return stringsString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().map { it }
    }

    @TypeConverter
    fun stringFromList(list: List<String>): String {
        return list.joinToString(",")
    }
}