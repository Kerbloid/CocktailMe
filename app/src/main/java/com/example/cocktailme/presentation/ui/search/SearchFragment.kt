package com.example.cocktailme.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailme.R
import com.example.cocktailme.common.MarginItemDecoration
import com.example.cocktailme.databinding.FragmentSearchBinding
import com.example.cocktailme.presentation.ui.home.CocktailClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), CocktailClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()

    private lateinit var searchAdapter: SearchCocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchAdapter = SearchCocktailsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initViewModel()
        initUi()
        initRecyclerView(binding.rvSearch, searchAdapter)

        return root
    }

    private fun initUi() {
        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            viewModel.searchCocktail(text.toString())
        }
    }

    private fun initViewModel() {
        viewModel.searchCocktails.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it)
        }
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        cocktailAdapter: SearchCocktailsAdapter
    ) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cocktailAdapter
            addItemDecoration(MarginItemDecoration(20, 10, 20, 10))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCocktailSelected(id: String) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_search_to_navigation_cocktail_info, bundle)
    }
}