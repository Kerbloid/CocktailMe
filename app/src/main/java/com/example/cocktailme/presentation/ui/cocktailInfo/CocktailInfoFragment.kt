package com.example.cocktailme.presentation.ui.cocktailInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cocktailme.R
import com.example.cocktailme.databinding.FragmentCocktailInfoBinding
import kotlinx.android.synthetic.main.content_scrolling.view.*
import kotlinx.android.synthetic.main.fragment_cocktail_info.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CocktailInfoFragment(private val id: String): Fragment() {

    private var _binding: FragmentCocktailInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CocktailInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCocktail(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val text = binding.root.text
        val toolbar = binding.toolbarLayout
        val image = binding.drinkImage
        viewModel.cocktail.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.drinkThumb ?: R.mipmap.ic_launcher_round)
                .centerCrop()
                .into(image)
            toolbar.isSelected = true
            toolbar.title = it.name
            text.text = it.toString()
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
}