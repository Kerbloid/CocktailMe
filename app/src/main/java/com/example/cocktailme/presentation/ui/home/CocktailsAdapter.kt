package com.example.cocktailme.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cocktailme.R
import com.example.cocktailme.presentation.entities.CocktailItem

class CocktailsAdapter(
    private val context: Context,
    private val listener: CocktailClickListener
) :
    RecyclerView.Adapter<CocktailsAdapter.ViewHolder>() {

    private val cocktailItems: ArrayList<CocktailItem> = arrayListOf()

    override fun getItemCount(): Int {
        return cocktailItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cocktail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cocktailItems[position].also { cocktail ->
            with(holder) {
                Glide.with(context)
                    .load(cocktail.image ?: R.mipmap.ic_launcher_round)
                    .transform(CenterCrop(), RoundedCorners(25))
                    .into(image)
                alcoholic.text = cocktail.alcoholic ?: "Unknown"
                name.text = cocktail.name ?: "Unknown"
            }

            holder.cocktailItem.setOnClickListener {
                listener.onCocktailSelected(cocktail.id ?: return@setOnClickListener)
            }

//            holder.ivUnbookmark.setOnClickListener {
//                listener.unbookmark(book)
//            }
//
//            holder.ivBookmark.setOnClickListener {
//                listener.bookmark(book)
//            }

//            when (book.status) {
//                BookmarkStatus.BOOKMARKED -> {
//                    holder.ivBookmark.visibility = View.GONE
//                    holder.ivUnbookmark.visibility = View.VISIBLE
//                }
//                BookmarkStatus.UNBOOKMARKED -> {
//                    holder.ivBookmark.visibility = View.VISIBLE
//                    holder.ivUnbookmark.visibility = View.GONE
//                }
//            }
        }
    }

    fun submitUpdate(update: List<CocktailItem>) {
        val callback = BooksDiffCallback(cocktailItems, update)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)

        cocktailItems.clear()
        cocktailItems.addAll(update)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val alcoholic: TextView = view.findViewById(R.id.alcoholic)
        val name: TextView = view.findViewById(R.id.drinkName)
        val image: ImageView = view.findViewById(R.id.drinkImage)
        val cocktailItem: ConstraintLayout = view.findViewById(R.id.cocktailItem)
    }

    class BooksDiffCallback(
        private val oldCocktailItems: List<CocktailItem>,
        private val newCocktailItems: List<CocktailItem>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldCocktailItems.size
        }

        override fun getNewListSize(): Int {
            return newCocktailItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCocktailItems[oldItemPosition].id == newCocktailItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCocktailItems[oldItemPosition].name == newCocktailItems[newItemPosition].name ||
                    oldCocktailItems[oldItemPosition].image == newCocktailItems[newItemPosition].image ||
                    oldCocktailItems[oldItemPosition].alcoholic == newCocktailItems[newItemPosition].alcoholic
        }
    }
}