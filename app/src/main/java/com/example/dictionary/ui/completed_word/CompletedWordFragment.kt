package com.example.dictionary.ui.completed_word

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.data.model.Word
import com.example.dictionary.databinding.FragmentCompletedWordBinding
import com.example.dictionary.ui.container.ContainerViewModel
import com.example.dictionary.ui.adapter.WordAdapter
import com.example.dictionary.ui.container.ContainerFragmentDirections
import com.example.dictionary.utils.WordUtils
import kotlinx.coroutines.launch

class CompletedWordFragment : Fragment() {
    private lateinit var binding: FragmentCompletedWordBinding
    private lateinit var wordAdapter: WordAdapter
    private val parentViewModel: ContainerViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    ) { ContainerViewModel.factory }

    private var wordList: List<Word> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedWordBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        lifecycleScope.launch {
            //collects a list of new words from the parent viewModel and updates the RecyclerView adapter with it.
            parentViewModel.run {
                getAllCompletedWords().collect {
                    wordList = it
                    wordAdapter.setWords(it)
                    binding.tvEmptyRv.isInvisible = wordAdapter.itemCount != 0
                }
            }
        }

        binding.run {
            topnav.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("debugging", "newText = $newText")

                    val filteredWords = WordUtils.searchWord(newText, wordList)
                    Log.d("debugging", "filteredWords = $filteredWords")
                    Log.d("debugging", "wordList = $wordList")
                    if (filteredWords.isNotEmpty()) wordAdapter.setWords(filteredWords)
                    return true
                }
            })

            topnav.ibSort.setOnClickListener {
                WordUtils.showSortDialog(requireContext(), layoutInflater, wordList, wordAdapter)
            }
        }
    }

    private fun setupAdapter() {
        wordAdapter = WordAdapter(emptyList())

        wordAdapter.listener = object : WordAdapter.Listener {
            override fun onClick(word: Word) {
                findNavController().navigate(
                    ContainerFragmentDirections.actionContainerFragmentToSelectedWordFragment(word.id!!)
                )
            }
        }
        binding.rvCompletedWords.adapter = wordAdapter
        binding.rvCompletedWords.layoutManager = LinearLayoutManager(requireContext())
    }
}