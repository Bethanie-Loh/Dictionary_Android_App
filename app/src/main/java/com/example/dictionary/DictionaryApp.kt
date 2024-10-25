package com.example.dictionary

import android.app.Application
import androidx.room.Room
import com.example.dictionary.data.db.WordDatabase
import com.example.dictionary.data.repository.DictRepository

class DictionaryApp : Application() {
    lateinit var repo: DictRepository

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            this, WordDatabase::class.java, WordDatabase.NAME
        ).fallbackToDestructiveMigration().build()

        repo = DictRepository(db.wordDao())
    }
}