package com.alvarengadev.movieschallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alvarengadev.movieschallenge.data.domain.Movie
import com.alvarengadev.movieschallenge.databinding.ItemMovieBinding
import com.alvarengadev.movieschallenge.utils.format.FormatText
import com.squareup.picasso.Picasso

class MovieListAdapter: PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder.create(parent, onItemClickListener)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onItemClickListener: OnItemClickListener?
    ): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(movie: Movie) = binding.apply {
            with(movie) {
                textTitleMovie.text = originalTitle
                textDateMovie.text = releaseDate
                textDescriptionMovie.text = overview
                textDateMovie.text = FormatText.getShortDate(releaseDate)
                Picasso.get()
                    .load("$URL_IMAGE/$poster")
                    .into(imageMovie)

                itemView.setOnClickListener {
                    val positionRcy = layoutPosition
                    if (positionRcy != RecyclerView.NO_POSITION) {
                        onItemClickListener?.setOnItemClickListener(id)
                    }
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup, onItemClickListener: OnItemClickListener?): MovieViewHolder {
                val binding = ItemMovieBinding.inflate(
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