package com.example.dictionary.ui.selected_word

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.example.dictionary.R
import com.example.dictionary.core.Constants
import com.example.dictionary.databinding.FragmentSelectedWordBinding
import com.example.dictionary.databinding.LayoutDeleteCustomAlertDialogBinding
import com.example.dictionary.ui.container.ContainerViewModel
import kotlinx.coroutines.launch


class SelectedWordFragment : Fragment() {
    private lateinit var binding: FragmentSelectedWordBinding
    private val selectedViewModel: SelectedWordViewModel by viewModels { SelectedWordViewModel.factory }

    private var selectedWordId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedWordBinding.inflate(layoutInflater, container, false)

        //get the id
        selectedWordId = requireArguments().getInt("id", 0)
        selectedViewModel.getWord(selectedWordId)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            var clicked = selectedViewModel.getCurrentStatus(selectedWordId)
            changeLearnButtonUI(clicked)

            //to display the word's details in the input boxes at selected.xml layout
            selectedViewModel.word.observe(viewLifecycleOwner) { binding.word = it }

            binding.btnUpdate.setOnClickListener {
                findNavController().navigate(
                    SelectedWordFragmentDirections.actionSelectedWordFragmentToUpdateWordFragment(
                        selectedWordId
                    )
                )
            }

            binding.btnDelete.setOnClickListener { showDeleteAlertDialog() }

            binding.btnLearn.setOnClickListener {
                Log.d("debugging", "SelectedWordFragment -> clickLearnBtn")

                Log.d("debugging", "before clicked: $clicked")
                clicked = !clicked
                Log.d("debugging", "after clicked: $clicked")
                changeLearnButtonUI(clicked)

                selectedViewModel.changeLearnStatus(selectedWordId, clicked)

                val bundle = Bundle().apply { putBoolean(Constants.REFRESH, true) }
                setFragmentResult(Constants.CHANGE_STATUS, bundle)

                requireActivity().onBackPressedDispatcher.addCallback {
                    setFragmentResult(Constants.CHANGE_STATUS, bundle)
                    findNavController().popBackStack()
                }
            }
        }

    }

    private fun showDeleteAlertDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val customLayout = LayoutDeleteCustomAlertDialogBinding.inflate(layoutInflater)

        customLayout.run {
            customLayout.btnCancel.setOnClickListener { alertDialog.dismiss() }
            btnDelete.setOnClickListener {
                selectedViewModel.deleteWord()
                alertDialog.dismiss()
                Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        }

        alertDialog.setView(customLayout.root)
        alertDialog.show()
    }

    private fun changeLearnButtonUI(click: Boolean) {
        Log.d("debugging", "changeLearnButtonUI")

        val bgColor = if (click) R.color.green else R.color.sun
        val icon =  if (click) R.drawable.ic_read else R.drawable.ic_unread
        val text =  if (click) "Learnt" else "Learn"

        binding.btnLearn.apply {
            backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), bgColor))
            setIconResource(icon)
            setText(text)
        }
    }

}