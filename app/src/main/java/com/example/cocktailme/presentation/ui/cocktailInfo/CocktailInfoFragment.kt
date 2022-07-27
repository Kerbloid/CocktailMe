package com.example.cocktailme.presentation.ui.cocktailInfo

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cocktailme.R
import com.example.cocktailme.common.LayoutUtils
import com.example.cocktailme.common.gone
import com.example.cocktailme.common.showToast
import com.example.cocktailme.common.visible
import com.example.cocktailme.databinding.FragmentCocktailInfoBinding
import com.example.cocktailme.entities.Cocktail
import com.example.data.YOUTUBE_URL_REGEX
import com.google.android.material.chip.Chip
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.content_scrolling.*
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
        val root: View = binding.root
        val player = binding.root.youtube_player_view

        lifecycle.addObserver(player)

        initListeners()
        initViewModel()

        return root
    }

    private fun initListeners() {
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.fab.setOnClickListener {
            viewModel.saveOrRemoveFromFavorite()
        }
    }

    private fun initViewModel() {

        viewModel.cocktail.observe(viewLifecycleOwner) { cocktail ->
            if (cocktail == null) return@observe
            setCocktail(cocktail)
        }

        viewModel.isInFavorite.observe(viewLifecycleOwner) { isInFavorite ->
            if (isInFavorite) {
                binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.fab.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.purple_500))
            } else {
                binding.fab.setImageResource(R.drawable.ic_outline_not_favorite_border_24)
                binding.fab.imageTintList = ColorStateList.valueOf(requireContext().getColor(android.R.color.black))
            }
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> {
                    LayoutUtils.crossFade(
                        listOf(binding.pbLoadingDrink),
                        listOf(binding.root.cocktail_info, binding.appBar, tagsGroup)
                    )
                }
                false -> {
                    LayoutUtils.crossFade(
                        listOf(binding.root.cocktail_info, binding.appBar, tagsGroup),
                        listOf(binding.pbLoadingDrink)
                    )
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            requireActivity().showToast(
                when (error) {
                    is String -> error
                    else -> getString(error as Int)
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setCocktail(cocktail: Cocktail) {
        Log.d("Cocktail", "cocktail: $cocktail")
        binding.toolbarLayout.title = cocktail.name ?: getString(R.string.unknown)

        setCocktailImage(cocktail.drinkThumb)

        setInfo(viewModel.getInfo())

        setIngredients(viewModel.getIngredients())

        setInstructions(cocktail.instructions)

        setCocktailVideo(cocktail.video)

        setTags(cocktail.tags?.split(','))
    }

    private fun setCocktailImage(uri: String?) {
        Glide.with(requireContext())
            .load(uri ?: R.drawable.item_placeholder)
            .centerCrop()
            .into(binding.drinkImage)
    }

    private fun setTags(tags: List<String>?) {
        tags?.let {
            val tagsGroupView = binding.root.tagsGroup
            tagsGroupView.visible()
            tagsGroupView.removeAllViews()
            val inflater = LayoutInflater.from(requireContext())
            tags.forEach {
                val tag: Chip = inflater.inflate(R.layout.item_tag, tagsGroupView, false) as Chip
                tag.text = it
                tagsGroupView.addView(tag)
            }
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
            val measure = if (it.first == null) "" else "- ${it.first}"
            informationTextView.text =
                getString(R.string.ingredients_string_format, it.second, measure).trim()
            ingredientsView.addView(informationTextView)
        }
    }

    private fun setInstructions(instructions: String?) {
        Log.d("SASA", "instr: $instructions")
        val instructionsView = binding.root.cocktail_instructions
        if (instructions.isNullOrEmpty()) {
            instructionsView.gone()
            return
        }
        instructionsView.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        val instructionsTextView: TextView =
            inflater.inflate(R.layout.item_instructions_text, instructionsView, false) as TextView
        instructionsTextView.text = instructions.trim()
        instructionsView.addView(instructionsTextView)
    }

    private fun getYoutubeVideoId(link: String): String? {
        val regex = YOUTUBE_URL_REGEX.toRegex()
        return regex.find(link)?.value
    }

    private fun setCocktailVideo(videoUrl: String?) {
        Log.d("SASA", "url: $videoUrl")
        videoUrl?.let { url ->
            val videoView = binding.root.youtube_player_view
            videoView.visible()
            videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    val videoId = getYoutubeVideoId(url)
                    videoId?.let {
                        youTubePlayer.cueVideo(it, 0f)
                    }
                }
            })
        }
    }
}