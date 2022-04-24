package com.example.cocktailme.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailme.R
import com.example.cocktailme.databinding.FragmentCocktailsHomeBinding
import com.example.cocktailme.common.LayoutUtils
import com.example.cocktailme.common.MarginItemDecoration
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeCocktailsFragment : Fragment(), CocktailClickListener {

    private var _binding: FragmentCocktailsHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var popularCocktailsAdapter: CocktailsAdapter
    private lateinit var latestCocktailsAdapter: CocktailsAdapter
    private lateinit var randomCocktailsAdapter: CocktailsAdapter

    private val viewModel by viewModel<HomeCocktailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularCocktailsAdapter = CocktailsAdapter(this)
        latestCocktailsAdapter = CocktailsAdapter(this)
        randomCocktailsAdapter = CocktailsAdapter(this)
        lifecycleScope.launch {
            viewModel.getCocktails()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailsHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerViewRandom: RecyclerView = binding.rvRandom
        val recyclerViewPopular: RecyclerView = binding.rvPopular
        val recyclerViewLatest: RecyclerView = binding.rvLatest
        val progress: ProgressBar = binding.pbLoading
        val textPopular = binding.textPopular
        val textLatest = binding.textLatest
        val textRandom = binding.textRandom

        recyclerViewPopular.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularCocktailsAdapter
            addItemDecoration(MarginItemDecoration(20,5,20,5))
        }
        recyclerViewLatest.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = latestCocktailsAdapter
            addItemDecoration(MarginItemDecoration(20,5,20,5))
        }
        recyclerViewRandom.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = randomCocktailsAdapter
            addItemDecoration(MarginItemDecoration(20,5,20,5))
        }

        viewModel.popularCocktails.observe(viewLifecycleOwner) {
            popularCocktailsAdapter.submitList(it)
        }
        viewModel.latestCocktails.observe(viewLifecycleOwner) {
            latestCocktailsAdapter.submitList(it)
        }
        viewModel.randomCocktails.observe(viewLifecycleOwner) {
            randomCocktailsAdapter.submitList(it)
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> {
                    LayoutUtils.crossFade(
                        listOf(progress),
                        listOf(
                            textRandom,
                            textPopular,
                            textLatest,
                            recyclerViewRandom,
                            recyclerViewPopular,
                            recyclerViewLatest
                        )
                    )
                }
                false -> {
                    LayoutUtils.crossFade(
                        listOf(
                            textRandom,
                            textPopular,
                            textLatest,
                            recyclerViewRandom,
                            recyclerViewRandom,
                            recyclerViewPopular,
                            recyclerViewLatest
                        ),
                        listOf(progress)
                    )
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(
                requireContext(),
                when (error) {
                    is String -> error
                    else -> getString(error as Int)
                },
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCocktailSelected(id: String) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_cocktails_to_navigation_cocktail_info, bundle)
    }
}