package com.matheusvillela.tmdbupcomingmovies.util

import androidx.room.TypeConverter

class ListOfIntsConverter {
    @TypeConverter
    fun listFromString(genreIds: String): List<Int> {
        return genreIds.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            .map { Integer.parseInt(it) }
    }

    @TypeConverter
    fun stringFromList(list: List<Int>): String {
        return list.joinToString(",")
    }
}