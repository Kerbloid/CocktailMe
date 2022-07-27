package com.example.cocktailme.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailme.R
import com.example.cocktailme.common.LayoutUtils
import com.example.cocktailme.common.MarginItemDecoration
import com.example.cocktailme.databinding.FragmentFavoriteBinding
import com.example.cocktailme.presentation.ui.home.CocktailClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), CocktailClickListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoriteViewModel>()

    private lateinit var favoriteCocktailsAdapter: FavoriteCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteCocktailsAdapter = FavoriteCocktailsAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.getFavoriteCocktails()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView(binding.rvFavorite, favoriteCocktailsAdapter)
        initViewModel()

        return root
    }

    private fun initViewModel() {
        viewModel.favoriteCocktails.observe(viewLifecycleOwner) {
            favoriteCocktailsAdapter.submitList(it)
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> {
                    LayoutUtils.crossFade(
                        listOf(binding.pbLoading),
                        listOf(binding.rvFavorite)
                    )
                }
                false -> {
                    LayoutUtils.crossFade(
                        listOf(binding.rvFavorite),
                        listOf(binding.pbLoading)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        cocktailAdapter: FavoriteCocktailsAdapter
    ) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cocktailAdapter
            addItemDecoration(MarginItemDecoration(20, 10, 20, 10))
        }
    }

    override fun onCocktailSelected(id: String) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_favorite_to_navigation_cocktail_info, bundle)
    }
}