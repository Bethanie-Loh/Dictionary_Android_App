package com.example.dictionary.ui.container

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dictionary.data.model.Word
import com.example.dictionary.databinding.FragmentContainerBinding
import com.example.dictionary.databinding.LayoutSortAlertDialogBinding
import com.example.dictionary.ui.adapter.TabAdapter
import com.example.dictionary.ui.adapter.WordAdapter
import com.example.dictionary.ui.completed_word.CompletedWordFragment
import com.example.dictionary.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContainerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpTabs.adapter = TabAdapter(
            this@ContainerFragment,
            listOf(HomeFragment(), CompletedWordFragment())
        )

        TabLayoutMediator(binding.tlTabs, binding.vpTabs) { tab, position ->
            when (position) {
                0 -> tab.text = "All Words"
                else -> tab.text = "Completed Words"
            }
        }.attach()

    }
}