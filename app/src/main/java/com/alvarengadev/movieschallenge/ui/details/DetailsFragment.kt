package com.alvarengadev.movieschallenge.ui.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alvarengadev.movieschallenge.MainActivity
import com.alvarengadev.movieschallenge.R
import com.alvarengadev.movieschallenge.databinding.FragmentDetailsBinding
import com.alvarengadev.movieschallenge.utils.*
import com.alvarengadev.movieschallenge.utils.format.FormatText
import com.alvarengadev.movieschallenge.utils.remote.ResultUtils
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()
    private val navArgs: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()
    }

    private fun setupObservers() = viewModel.apply {
        getMovieDetailsFromServer(navArgs.movieId)
        isMovieInFavoritesFromDatabase(navArgs.movieId)

        statusGetMovieDetailsFromServer.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                ResultUtils.Status.LOADING -> {
                    binding.progressBar.visible()
                }
                ResultUtils.Status.SUCCESS -> {
                    result.data?.let { movie ->
                        (activity as MainActivity).supportActionBar?.title = movie.title
                        with(binding) {
                            textTitleMovieDetails.text = movie.originalTitle
                            textReleaseDetails.text = FormatText.getLongDate(movie.releaseDate)
                            textGenreDetails.text = movie.listGenres
                            textDescriptionDetails.text = movie.overview

                            Picasso.get()
                                .load("${URL_IMAGE}/${movie.posterPrincipal}")
                                .into(imageMovieDetails)

                            containerMovieDetails.visible()
                            progressBar.gone()
                        }
                        viewModel.movie = movie
                    }
                }
                else -> {
                }
            }
        }

        statusInsertMovieInFavoriteFromDatabase.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                ResultUtils.Status.SUCCESS -> {
                    context?.toast("Add in favorites")
                }
                ResultUtils.Status.ERROR -> {
                    context?.toast("Error in operator")
                }
                else -> {
                }
            }
        }

        statusRemoveMovieInFavoritesFromDatabase.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                ResultUtils.Status.SUCCESS -> {
                    context?.toast("Remove movie from favorites")
                }
                ResultUtils.Status.ERROR -> {
                    context?.toast("Error in operator")
                }
                else -> {
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuActionFavorite = menu.findItem(R.id.action_favorites)
        val menuActionRemoveFavorite = menu.findItem(R.id.action_remove_favorites)
        menuActionFavorite.setEnabled(false).isVisible = false
        menuActionRemoveFavorite.setEnabled(false).isVisible = false

        viewModel.statusIsMovieInFavoritesFromDatabase.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                ResultUtils.Status.SUCCESS -> {
                    result.data?.let { isMovieInFavorites ->
                        if (isMovieInFavorites) {
                            menuActionRemoveFavorite.setEnabled(true).isVisible = true
                        } else {
                            menuActionFavorite.setEnabled(true).isVisible = true
                        }
                    }
                }
                else -> {
                }
            }
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                with(viewModel) {
                    movie?.let {
                        insertMovieInFavoriteFromDatabase(it)
                        item.setIcon(R.drawable.ic_favorite)
                    }
                }
                true
            }
            R.id.action_remove_favorites -> {
                with(viewModel) {
                    movie?.let {
                        removeMovieInFavoritesFromDatabase(it)
                        item.setIcon(R.drawable.ic_favorite_outline)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
    }

}