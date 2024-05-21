package com.example.dictionary.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.data.model.Word
import com.example.dictionary.databinding.LayoutWordItemBinding

class WordAdapter(
    private var words: List<Word>
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = LayoutWordItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return WordViewHolder(binding)
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(words[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    fun getAllCurrentWords() = words

    inner class WordViewHolder(
        private val binding: LayoutWordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            binding.word = word
            Log.d("debugging", "Total words: ${words.size}")
            binding.mcWordItem.setOnClickListener {
                listener?.onClick(word)
            }
        }
    }

    interface Listener {
        fun onClick(word: Word)
    }

}


//package com.example.dictionary.ui.adapter
//
//import com.example.dictionary.data.model.Word
//
//interface WordAdapter {
//    fun setWords(words: List<Word>)
//}
