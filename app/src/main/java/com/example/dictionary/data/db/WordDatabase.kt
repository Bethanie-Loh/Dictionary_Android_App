package com.example.dictionary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dictionary.data.model.Word

@Database(entities = [Word::class], version = 1)

abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        const val NAME = "dictionary_database"
    }
}