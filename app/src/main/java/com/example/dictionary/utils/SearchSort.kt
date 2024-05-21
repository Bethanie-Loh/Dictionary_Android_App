package com.example.dictionary.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.dictionary.data.model.Word
import com.example.dictionary.databinding.LayoutSortAlertDialogBinding
import com.example.dictionary.ui.adapter.WordAdapter

object WordUtils {

    fun searchWord(query: String?, wordList: List<Word>): List<Word> {
        return if (query.isNullOrBlank()) {
            wordList
        } else {
            wordList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }

    fun showSortDialog(
        context: Context,
        inflater: LayoutInflater,
        wordList: List<Word>,
        adapter: WordAdapter
    ) {

        Log.d("debugging", "WordUtils -> showSortDialog")

        val sortDialog = AlertDialog.Builder(context).create()
        val customLayout = LayoutSortAlertDialogBinding.inflate(inflater)

        var sortOrder = ""
        var sortType = ""
        customLayout.run {
            btnDoneSort.setOnClickListener {
                sortOrder = if (rbAsc.isChecked) "asc" else "desc"
                sortType = if (rbTitle.isChecked) "title" else "date"
                Log.d(
                    "debugging",
                    "sortOrder: $sortOrder, sortType: $sortType, wordList: $wordList"
                )

                val sortedWords = sortWords(sortOrder, sortType, wordList)
                if (sortedWords.isNotEmpty()) adapter.setWords(sortedWords)
                sortDialog.dismiss()
            }
        }
        sortDialog.setView(customLayout.root)
        sortDialog.show()
    }

    private fun sortWords(sortOrder: String, sortType: String, wordList: List<Word>): List<Word> {
        return when (sortType) {
            "title" -> {
                if (sortOrder == "asc") wordList.sortedBy { it.title }
                else wordList.sortedByDescending { it.title }
            }

            "date" -> {
                if (sortOrder == "asc") wordList.sortedBy { it.date }
                else wordList.sortedByDescending { it.date }
            }

            else -> wordList
        }
    }
}

