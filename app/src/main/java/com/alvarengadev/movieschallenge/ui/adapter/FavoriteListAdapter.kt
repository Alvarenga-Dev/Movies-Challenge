package com.alvarengadev.movieschallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.movieschallenge.data.domain.Movie
import com.alvarengadev.movieschallenge.databinding.ItemFavoriteBinding
import com.squareup.picasso.Picasso

class FavoriteListAdapter: ListAdapter<Movie, FavoriteListAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder.create(parent, onItemClickListener)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    class MovieViewHolder(
        private val binding: ItemFavoriteBinding,
        private val onItemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movie: Movie) = binding.apply {
            with(movie) {
                textTitleMovie.text = originalTitle
                Picasso.get()
                    .load("$URL_IMAGE/$poster")
                    .into(imageMovie)

                itemView.setOnClickListener {
                    val positionRcy = adapterPosition
                    if (positionRcy != RecyclerView.NO_POSITION) {
                        onItemClickListener?.setOnItemClickListener(id)
                    }
                }
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener?
            ): MovieViewHolder {
                val binding = ItemFavoriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MovieViewHolder(binding, onItemClickListener)
            }

            const val URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

        }
    }

}