package com.example.dictionary.ui.update_word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dictionary.databinding.FragmentUpdateWordBinding


class UpdateWordFragment : Fragment() {
    private lateinit var binding: FragmentUpdateWordBinding
    private val updateViewModel: UpdateWordViewModel by viewModels { UpdateWordViewModel.factory }
    private var selectedWordId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateWordBinding.inflate(layoutInflater, container, false)

        selectedWordId = requireArguments().getInt("id", 0)
        updateViewModel.getWord(selectedWordId)

        updateViewModel.run {
            words.observe(viewLifecycleOwner) { binding.word = it }
            toastMsg.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnUpdateWord.setOnClickListener {
                val title = etTitle.text.toString()
                val meaning = etMeaning.text.toString()
                val synonyms = etSynonyms.text.toString()
                val usage = etUsage.text.toString()
                if (title.isBlank() || meaning.isBlank() || synonyms.isBlank() || usage.isBlank()) {
                    Toast.makeText(
                        requireContext(),
                        "All inputs must be filled",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    updateViewModel.updateWord(title, meaning, synonyms, usage)
                    findNavController().navigate(
                        UpdateWordFragmentDirections.actionUpdateWordFragmentToContainerFragment()
                    )
                    Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}