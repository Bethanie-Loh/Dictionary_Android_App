package com.example.dictionary.ui.add_word

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dictionary.core.Constants
import com.example.dictionary.databinding.FragmentAddWordBinding
import kotlinx.coroutines.launch


class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    private val addViewModel: AddWordViewModel by viewModels { AddWordViewModel.factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("debugging", "AddWordFragment -> onViewCreated")
        binding.viewModel = addViewModel //to connect the addViewModel to the layout
        binding.lifecycleOwner =
            viewLifecycleOwner //It connects UI components to lifecycle events, ensuring updates reflect accurately.

        //Listens for ViewModel changes, communicates to previous fragment, and returns to previous page if needed.
        lifecycleScope.launch {
            Log.d("debugging", "AddWordFragment -> lifecycleScope.launch")
            addViewModel.run {
                toastMsg.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                finish.collect { findNavController().popBackStack() }
            }
        }
    }
}