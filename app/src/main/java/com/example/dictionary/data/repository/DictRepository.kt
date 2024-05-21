package com.example.dictionary.data.repository

import com.example.dictionary.data.db.WordDao
import com.example.dictionary.data.model.Word
import kotlinx.coroutines.flow.Flow

class DictRepository(
    private val dao: WordDao
) {

    fun getAllNewWords(): Flow<List<Word>> = dao.getAllNewWords()

    fun getAllCompletedWords(): Flow<List<Word>> = dao.getAllCompletedWords()

    fun getWordById(id: Int) = dao.getWordById(id)

    fun addWord(word: Word) = dao.addWord(word)

    fun deleteWord(word: Word) = dao.deleteWord(word)

    fun updateWord(word: Word) = dao.updateWord(word)

    fun getStatus(id: Int): Boolean = dao.getStatus(id)

    fun updateStatus(id: Int, completed: Boolean) = dao.updateStatus(id, completed)
}