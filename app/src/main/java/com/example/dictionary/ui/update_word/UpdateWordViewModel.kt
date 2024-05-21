package com.example.dictionary.ui.update_word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dictionary.DictionaryApp
import com.example.dictionary.data.model.Word
import com.example.dictionary.data.repository.DictRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateWordViewModel(
    private val repo: DictRepository
) : ViewModel() {

    private val _word: MutableLiveData<Word> = MutableLiveData()
    val words: LiveData<Word> = _word
    val toastMsg: MutableLiveData<String> = MutableLiveData()

    fun getWord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _word.postValue(repo.getWordById(id))
        }
    }

    fun updateWord(
        updatedTitle: String,
        updatedMeaning: String,
        updatedSynonyms: String,
        updatedUsage: String
    ) {
        if (words.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.updateWord(
                    words.value!!.copy(
                        title = updatedTitle,
                        meaning = updatedMeaning,
                        synonym = updatedSynonyms,
                        usage = updatedUsage
                    )
                )
            }
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val repo = (this[APPLICATION_KEY] as DictionaryApp).repo
                UpdateWordViewModel(repo)
            }
        }
    }
}