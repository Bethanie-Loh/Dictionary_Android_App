package com.example.dictionary.ui.container

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dictionary.DictionaryApp
import com.example.dictionary.data.model.Word
import com.example.dictionary.data.repository.DictRepository
import kotlinx.coroutines.flow.Flow

class ContainerViewModel(
    private val repo: DictRepository
) : ViewModel() {

    fun getAllNewWords(): Flow<List<Word>> = repo.getAllNewWords()

    fun getAllCompletedWords(): Flow<List<Word>> = repo.getAllCompletedWords()

    companion object {
        val factory = viewModelFactory {
            initializer {
                val repo = (this[APPLICATION_KEY] as DictionaryApp).repo
                ContainerViewModel(repo)
            }
        }
    }
}