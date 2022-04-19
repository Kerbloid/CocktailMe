package com.example.cocktailme.presentation.ui.cocktailInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cocktailme.R
import com.example.cocktailme.databinding.FragmentCocktailInfoBinding
import com.example.cocktailme.presentation.ui.common.visible
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.content_scrolling.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CocktailInfoFragment: Fragment() {

    private var _binding: FragmentCocktailInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CocktailInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getString("id")
        id?.let { viewModel.getCocktail(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailInfoBinding.inflate(inflater, container, false)
        val context = requireContext()
        val root: View = binding.root
        val toolbar = binding.toolbarLayout
        val image = binding.drinkImage
        val backButton = binding.back
        val tagsGroup = root.tagsGroup

        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_cocktail_info_to_cocktails)
        }

        viewModel.cocktail.observe(viewLifecycleOwner) { cocktail ->
            toolbar.title = cocktail.name ?: getString(R.string.unknown)
            Glide.with(context)
                .load(cocktail.drinkThumb ?: R.drawable.item_placeholder)
                .centerCrop()
                .into(image)
            cocktail.tags?.let {
                tagsGroup.visible()
                setTags(it.split(','))
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setTags(tags: List<String>?) {
        val tagsGroup = binding.root.tagsGroup
        tagsGroup.removeAllViews()
        tags?.forEach {
            val inflater = LayoutInflater.from(requireContext())
            val tag: Chip = inflater.inflate(R.layout.item_tag, tagsGroup, false) as Chip
            tag.text = it
            tagsGroup.addView(tag)
        }
    }
}