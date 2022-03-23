package com.example.cocktailme.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailme.CocktailApplication
import com.example.cocktailme.LayoutUtils
import com.example.cocktailme.R
import com.example.cocktailme.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var drinksAdapter: DrinksAdapter

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory(
            ((requireActivity().application) as CocktailApplication).getRandomCocktailsUseCase,
            ((requireActivity().application) as CocktailApplication).cocktailMapper
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinksAdapter = DrinksAdapter(requireContext())
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
            drinksAdapter.submitUpdate(it)
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
            adapter = drinksAdapter
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.d("SASA", it)
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