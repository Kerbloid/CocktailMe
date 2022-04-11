package com.example.cocktailme.presentation.ui.randomCocktails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailme.databinding.FragmentHomeBinding
import com.example.cocktailme.presentation.LayoutUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomCocktailsFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var cocktailsAdapter: CocktailsAdapter

    private val viewModel by viewModel<RandomCocktailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cocktailsAdapter = CocktailsAdapter(requireContext())
        viewModel.getRandomCocktails()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.rvBooks
        val progress: ProgressBar = binding.pbLoading

        viewModel.cocktails.observe(viewLifecycleOwner) {
            cocktailsAdapter.submitUpdate(it)
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> LayoutUtils.crossFade(progress, recyclerView)
                false -> LayoutUtils.crossFade(recyclerView, progress)
            }
        }

        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = cocktailsAdapter
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}