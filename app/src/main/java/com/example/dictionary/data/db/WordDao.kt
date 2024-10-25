package com.example.dictionary.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    //we use Flow to allow data to be fetched asynchronously

    @Query("SELECT * FROM Word WHERE status = 0")
    fun getAllNewWords(): Flow<List<Word>>

    @Query("SELECT * FROM Word WHERE status = 1")
    fun getAllCompletedWords(): Flow<List<Word>>

    @Query("SELECT * FROM Word WHERE id = :id")
    fun getWordById(id: Int): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: Word)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("SELECT status FROM Word WHERE id = :id")
    fun getStatus(id: Int): Boolean

    @Query("UPDATE Word SET status = :status WHERE id = :id")
    fun updateStatus(id: Int, status: Boolean)
}