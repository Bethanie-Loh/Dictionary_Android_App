package com.example.dictionary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//marks the class as a database table
@Entity
data class Word(
    //to auto generate unique ids for each database entry
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val usage: String,
    var status: Boolean? = false,
    var date: String = "12/2/2024"
)
