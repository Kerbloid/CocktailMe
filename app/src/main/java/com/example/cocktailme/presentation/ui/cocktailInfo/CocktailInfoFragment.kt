package com.example.cocktailme.presentation.ui.cocktailInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cocktailme.R
import com.example.cocktailme.databinding.FragmentCocktailInfoBinding
import com.example.cocktailme.common.LayoutUtils
import com.example.cocktailme.common.gone
import com.example.cocktailme.common.visible
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.content_scrolling.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CocktailInfoFragment : Fragment() {

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
        val appBar = binding.appBar
        val tagsGroup = root.tagsGroup
        val progress = binding.pbLoadingDrink
        val cocktailInfoLayout = root.cocktail_info

        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_cocktail_info_to_cocktails)
        }

        viewModel.cocktail.observe(viewLifecycleOwner) { cocktail ->
            toolbar.title = cocktail?.name ?: getString(R.string.unknown)
            Glide.with(context)
                .load(cocktail?.drinkThumb ?: R.drawable.item_placeholder)
                .centerCrop()
                .into(image)
            cocktail?.tags?.let {
                tagsGroup.visible()
                setTags(it.split(','))
            }
            setInfo(
                listOf(
                    Pair("IBA", cocktail?.IBA),
                    Pair("Alcoholic", cocktail?.alcoholic?.lowercase()),
                    Pair("Category", cocktail?.category?.lowercase()),
                    Pair("Glass", cocktail?.glass)
                )
            )
            setIngredients(
                listOf(
                    Pair(cocktail?.measure1, cocktail?.ingredient1),
                    Pair(cocktail?.measure2, cocktail?.ingredient2),
                    Pair(cocktail?.measure3, cocktail?.ingredient3),
                    Pair(cocktail?.measure4 as String?, cocktail?.ingredient4 as String?),
                    Pair(cocktail?.measure5 as String?, cocktail?.ingredient5 as String?)
                )
            )
            setInstructions(cocktail?.instructions)
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> {
                    LayoutUtils.crossFade(
                        listOf(progress),
                        listOf(cocktailInfoLayout, appBar)
                    )
                }
                false -> {
                    LayoutUtils.crossFade(
                        listOf(cocktailInfoLayout, appBar),
                        listOf(progress)
                    )
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(
                context,
                when (error) {
                    is String -> error
                    else -> getString(error as Int)
                },
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setTags(tags: List<String>) {
        val tagsGroup = binding.root.tagsGroup
        tagsGroup.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        tags.forEach {
            val tag: Chip = inflater.inflate(R.layout.item_tag, tagsGroup, false) as Chip
            tag.text = it
            tagsGroup.addView(tag)
        }
    }

    private fun setInfo(info: List<Pair<String, String?>>) {
        val information = binding.root.cocktail_info_list
        information.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        info.forEach {
            if (it.second.isNullOrEmpty()) return@forEach
            val informationTextView: TextView =
                inflater.inflate(R.layout.item_info_text, information, false) as TextView
            informationTextView.text = getString(R.string.info_string_format, it.first, it.second)
            information.addView(informationTextView)
        }
    }

    private fun setIngredients(info: List<Pair<Any?, Any?>>) {
        val ingredients = binding.root.cocktail_ingredients_list
        ingredients.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        Log.d("SASA", info.toString())
        info.forEach {
            if (it.second == null) return@forEach
            val informationTextView: TextView =
                inflater.inflate(R.layout.item_info_text, ingredients, false) as TextView
            val measure = if (it.first == null) "" else it.first
            informationTextView.text = getString(R.string.ingredients_string_format, measure, it.second).trim()
            ingredients.addView(informationTextView)
        }
    }

    private fun setInstructions(instr: String?) {
        val instructions = binding.root.cocktail_instructions
        if (instr.isNullOrEmpty()) instructions.gone()
        instructions.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        val instructionsTextView: TextView = inflater.inflate(R.layout.item_info_text, instructions, false) as TextView
        instructionsTextView.text = instr
        instructions.addView(instructionsTextView)
    }
}