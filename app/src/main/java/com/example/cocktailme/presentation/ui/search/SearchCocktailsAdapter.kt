package com.example.cocktailme.presentation.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cocktailme.R
import com.example.cocktailme.common.visible
import com.example.cocktailme.databinding.ItemCocktailFavoriteBinding
import com.example.cocktailme.entities.CocktailItem
import com.example.cocktailme.presentation.ui.home.CocktailClickListener

class SearchCocktailsAdapter (private val listener: CocktailClickListener) :
    ListAdapter<CocktailItem, SearchCocktailsAdapter.CocktailViewHolder>(CocktailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        return CocktailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cocktail_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktailItem = getItem(position)
        holder.bind(cocktailItem)
    }

    open inner class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCocktailFavoriteBinding.bind(itemView)
        fun bind(cocktail: CocktailItem) {
            val context = itemView.context
            Glide.with(context)
                .load(cocktail.image ?: R.drawable.item_placeholder)
                .transform(CenterCrop(), RoundedCorners(25))
                .into(binding.drinkImage)
            binding.alcoholic.text = cocktail.alcoholic ?: context.getString(R.string.unknown)
            binding.drinkName.text = cocktail.name ?: context.getString(R.string.unknown)
            binding.ingredients.visible(!cocktail.ingredients.isNullOrEmpty())
            binding.ingredients.text =
                cocktail.ingredients.toString().replace("]", "").replace("[", "")
            itemView.setOnClickListener {
                listener.onCocktailSelected(cocktail.id ?: return@setOnClickListener)
            }
        }
    }
}

class CocktailsDiffCallback : DiffUtil.ItemCallback<CocktailItem>() {
    override fun areItemsTheSame(oldItem: CocktailItem, newItem: CocktailItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CocktailItem, newItem: CocktailItem): Boolean {
        return oldItem.name == newItem.name ||
                oldItem.image == newItem.image ||
                oldItem.alcoholic == newItem.alcoholic ||
                oldItem.ingredients == newItem.ingredients
    }
}