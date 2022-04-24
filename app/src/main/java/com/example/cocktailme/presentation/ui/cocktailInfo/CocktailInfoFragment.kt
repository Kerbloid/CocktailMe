package com.example.cocktailme.presentation.ui.cocktailInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cocktailme.R
import com.example.cocktailme.common.LayoutUtils
import com.example.cocktailme.common.gone
import com.example.cocktailme.common.visible
import com.example.cocktailme.databinding.FragmentCocktailInfoBinding
import com.example.data.YOUTUBE_URL_REGEX
import com.google.android.material.chip.Chip
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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
        val player = binding.root.youtube_player_view

        lifecycle.addObserver(player)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_cocktail_info_to_cocktails)
        }

        viewModel.cocktail.observe(viewLifecycleOwner) { cocktail ->
            if (cocktail == null) return@observe
            Log.d("SASA", "cocktail: $cocktail")
            toolbar.title = cocktail.name ?: getString(R.string.unknown)
            Glide.with(context)
                .load(cocktail.drinkThumb ?: R.drawable.item_placeholder)
                .centerCrop()
                .into(image)
            cocktail.tags?.let {
                tagsGroup.visible()
                setTags(it.split(','))
            }
            setInfo(
                listOf(
                    getString(R.string.iba) to cocktail.IBA,
                    getString(R.string.alcoholic) to cocktail.alcoholic?.lowercase(),
                    getString(R.string.category) to cocktail.category?.lowercase(),
                    getString(R.string.glass) to cocktail.glass
                )
            )
            setIngredients(
                listOf(
                    cocktail.measure1 to cocktail.ingredient1,
                    cocktail.measure2 to cocktail.ingredient2,
                    cocktail.measure3 to cocktail.ingredient3,
                    cocktail.measure4 as String? to  cocktail.ingredient4 as String?,
                    cocktail.measure5 as String? to cocktail.ingredient5 as String?,
                    cocktail.measure6 as String? to cocktail.ingredient6 as String?,
                    cocktail.measure7 as String? to cocktail.ingredient7 as String?,
                    cocktail.measure8 as String? to cocktail.ingredient8 as String?,
                    cocktail.measure9 as String? to cocktail.ingredient9 as String?,
                    cocktail.measure10 as String? to cocktail.ingredient10 as String?
                )
            )
            setInstructions(cocktail.instructions)

            cocktail.video?.let { url ->
                player.visible()
                player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                        val videoId = getYoutubeVideoId(url)
                        videoId?.let {
                            youTubePlayer.cueVideo(it, 0f)
                        }
                    }
                })
            }
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> {
                    LayoutUtils.crossFade(
                        listOf(progress),
                        listOf(cocktailInfoLayout, appBar, tagsGroup)
                    )
                }
                false -> {
                    LayoutUtils.crossFade(
                        listOf(cocktailInfoLayout, appBar, tagsGroup),
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
        val tagsGroupView = binding.root.tagsGroup
        tagsGroupView.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        tags.forEach {
            val tag: Chip = inflater.inflate(R.layout.item_tag, tagsGroupView, false) as Chip
            tag.text = it
            tagsGroupView.addView(tag)
        }
    }

    private fun setInfo(informationList: List<Pair<String, String?>>) {
        val informationView = binding.root.cocktail_info_list
        informationView.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        informationList.forEach {
            if (it.second.isNullOrEmpty()) return@forEach
            val informationTextView: TextView =
                inflater.inflate(R.layout.item_information_text, informationView, false) as TextView
            informationTextView.text = getString(R.string.info_string_format, it.first, it.second)
            informationView.addView(informationTextView)
        }
    }

    private fun setIngredients(ingredientList: List<Pair<Any?, Any?>>) {
        val ingredientsView = binding.root.cocktail_ingredients_list
        ingredientsView.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        ingredientList.forEach {
            if (it.second == null || it.second == "") return@forEach
            val informationTextView: TextView =
                inflater.inflate(R.layout.item_ingredient_text, ingredientsView, false) as TextView
            val measure = if (it.first == null) "" else it.first
            informationTextView.text = getString(R.string.ingredients_string_format, measure, it.second).trim()
            ingredientsView.addView(informationTextView)
        }
    }

    private fun setInstructions(instructions: String?) {
        val instructionsView = binding.root.cocktail_instructions
        if (instructions.isNullOrEmpty()) {
            instructionsView.gone()
            return
        }
        instructionsView.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        val instructionsTextView: TextView = inflater.inflate(R.layout.item_instructions_text, instructionsView, false) as TextView
        instructionsTextView.text = instructions.trim()
        instructionsView.addView(instructionsTextView)
    }

    private fun getYoutubeVideoId(link: String): String? {
        val regex = YOUTUBE_URL_REGEX.toRegex()
        return regex.find(link)?.value
    }
}