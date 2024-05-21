package com.example.dictionary.ui.add_word

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dictionary.DictionaryApp
import com.example.dictionary.data.model.Word
import com.example.dictionary.data.repository.DictRepository
import com.example.dictionary.utils.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AddWordViewModel(
    private val repo: DictRepository
) : ViewModel() {

    val title: MutableLiveData<String> = MutableLiveData()
    val meaning: MutableLiveData<String> = MutableLiveData()
    val synonym: MutableLiveData<String> = MutableLiveData()
    val usage: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val toastMsg: MutableLiveData<String> = MutableLiveData()
    private val formattedDate = Date().getCurrentDate()

    fun submit() {
        if (title.value != null && meaning.value != null && synonym.value != null && usage.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.addWord(
                    Word(
                        title = title.value!!,
                        meaning = meaning.value!!,
                        synonym = synonym.value!!,
                        usage = usage.value!!,
                        status = false,
                        date = formattedDate
                    )
                )
                finish.emit(Unit)
            }
        } else {
            toastMsg.postValue("All inputs must be filled")
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val repo = (this[APPLICATION_KEY] as DictionaryApp).repo
                AddWordViewModel(repo)
            }
        }
    }
}