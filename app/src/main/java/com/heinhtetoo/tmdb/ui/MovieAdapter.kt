package com.heinhtetoo.tmdb.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.heinhtetoo.tmdb.R
import com.heinhtetoo.tmdb.data.entities.Movie
import com.heinhtetoo.tmdb.databinding.ItemMovieBinding

class MovieAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    interface MovieItemListener {
        fun onItemClick(id: Int, posterView: View, titleView: View)
        fun onFavoriteClick(id: Int, flag: Boolean)
    }

    private val items = ArrayList<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(items[position])
}

class MovieViewHolder(
    private val itemBinding: ItemMovieBinding,
    private val listener: MovieAdapter.MovieItemListener
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: Movie) {
        itemBinding.tvName.text = item.title

        Glide.with(itemBinding.root)
            .load(item.getFullPosterPath())
            .placeholder(android.R.color.darker_gray)
            .into(itemBinding.ivPoster)

        val resIdFav =
            if (item.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border

        itemBinding.btnFav.setImageResource(resIdFav)

        ViewCompat.setTransitionName(itemBinding.ivPoster, item.getPosterTransactionName())
        ViewCompat.setTransitionName(itemBinding.tvName, item.getTitleTransactionName())

        itemBinding.btnFav.setOnClickListener {
            listener.onFavoriteClick(item.id, !item.isFavorite)
        }
        itemBinding.root.setOnClickListener {
            listener.onItemClick(item.id, itemBinding.ivPoster, itemBinding.tvName)
        }
    }

}

