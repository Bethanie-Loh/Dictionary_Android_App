package com.example.dictionary.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.data.model.Word
import com.example.dictionary.databinding.FragmentHomeBinding
import com.example.dictionary.databinding.LayoutSortAlertDialogBinding
import com.example.dictionary.ui.container.ContainerViewModel
import com.example.dictionary.ui.adapter.WordAdapter
import com.example.dictionary.ui.container.ContainerFragmentDirections
import com.example.dictionary.utils.WordUtils
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var wordAdapter: WordAdapter
    private val parentViewModel: ContainerViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    ) { ContainerViewModel.factory }

    private var wordList: List<Word> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        //Initiates a coroutine within the current lifecycle scope.
        lifecycleScope.launch {
            //collects a list of new words from the parent viewModel and updates the RecyclerView adapter with it.
            parentViewModel.run {
                getAllNewWords().collect {
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

            fabAdd.setOnClickListener {
                findNavController().navigate(
                    ContainerFragmentDirections.actionContainerFragmentToAddWordFragment()
                )
            }
        }
    }

    private fun setupAdapter() {
        wordAdapter = WordAdapter(emptyList())

        Log.d("debugging", "HomeFragment -> setupAdapter()")
        //when clicking on each word item
        wordAdapter.listener = object : WordAdapter.Listener {
            override fun onClick(word: Word) {
                findNavController().navigate(
                    ContainerFragmentDirections.actionContainerFragmentToSelectedWordFragment(word.id!!)
                )
            }
        }
        binding.rvNewWords.adapter = wordAdapter
        binding.rvNewWords.layoutManager = LinearLayoutManager(requireContext())
    }
}
