package com.example.cocktailme.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cocktailme.R
import com.example.cocktailme.presentation.entities.Cocktail

class CocktailsAdapter (
    private val context: Context
//    ,
//    private val listener: ActionClickListener
) :
    RecyclerView.Adapter<CocktailsAdapter.ViewHolder>() {

    private val cocktails: ArrayList<Cocktail> = arrayListOf()

    override fun getItemCount(): Int {
        return cocktails.size
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
        cocktails[position].also { cocktail ->
            with(holder) {
                Glide.with(context)
                    .load(cocktail.image)
                    .transform(CenterCrop(), RoundedCorners(25))
                    .into(image)
//                id.text = cocktail.id
                name.text = cocktail.name
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

    fun submitUpdate(update: List<Cocktail>) {
        val callback = BooksDiffCallback(cocktails, update)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)

        cocktails.clear()
        cocktails.addAll(update)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val id: TextView = view.findViewById(R.id.drinkId)
        val name: TextView = view.findViewById(R.id.drinkName)
        val image: ImageView = view.findViewById(R.id.drinkImage)
//        val ivBookmark: ImageView = view.findViewById(R.id.ivBookmark)
//        val ivUnbookmark: ImageView = view.findViewById(R.id.ivUnbookmark)
    }

    class BooksDiffCallback(
        private val oldCocktails: List<Cocktail>,
        private val newCocktails: List<Cocktail>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldCocktails.size
        }

        override fun getNewListSize(): Int {
            return newCocktails.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCocktails[oldItemPosition].id == newCocktails[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCocktails[oldItemPosition].name == newCocktails[newItemPosition].name ||
                    oldCocktails[oldItemPosition].image == newCocktails[newItemPosition].image
        }
    }

//    interface ActionClickListener {
//        fun bookmark(book: BookWithStatus)
//        fun unbookmark(book: BookWithStatus)
//    }
}