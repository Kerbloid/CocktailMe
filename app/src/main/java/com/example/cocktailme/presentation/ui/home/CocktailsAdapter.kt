package com.example.cocktailme.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cocktailme.R
import com.example.cocktailme.presentation.entities.CocktailItem

class CocktailsAdapter(private val listener: CocktailClickListener) :
    ListAdapter<CocktailItem, CocktailsAdapter.CocktailViewHolder>(CocktailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        return CocktailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cocktail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktailItem = getItem(position)
        holder.bind(cocktailItem)
    }

    open inner class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.drinkName)
        val image: ImageView = itemView.findViewById(R.id.drinkImage)
        val alcoholic: TextView = itemView.findViewById(R.id.alcoholic)
        fun bind(cocktail: CocktailItem) {
            val context = itemView.context
            Glide.with(context)
                .load(cocktail.image ?: R.drawable.item_placeholder)
                .transform(CenterCrop(), RoundedCorners(25))
                .into(image)
            alcoholic.text = cocktail.alcoholic ?: context.getString(R.string.unknown)
            name.text = cocktail.name ?: context.getString(R.string.unknown)
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
                oldItem.alcoholic == newItem.alcoholic
    }
}