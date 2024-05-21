package com.example.dictionary.ui.selected_word

import android.util.Log
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectedWordViewModel(
    private val repo: DictRepository
) : ViewModel() {

    private val _word: MutableLiveData<Word> = MutableLiveData()
    val word: LiveData<Word> = _word

    fun getWord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _word.postValue(repo.getWordById(id))
            //postValue : Sets a new value for the LiveData object.
        }
    }

    fun deleteWord() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteWord(word.value!!)
        }
    }

    fun changeLearnStatus(id: Int, completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateStatus(id, completed)
        }
    }

    /* suspend: Allows function to be paused and resumed.
    Enables asynchronous execution without blocking. */
    suspend fun getCurrentStatus(id: Int): Boolean {
        //withContext: Switches to a specific coroutine context.
        return withContext(Dispatchers.IO) {
            val status = repo.getStatus(id)
            status
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val repo = (this[APPLICATION_KEY] as DictionaryApp).repo
                SelectedWordViewModel(repo)
            }
        }
    }
}