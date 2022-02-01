package com.alvarengadev.movieschallenge.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alvarengadev.movieschallenge.databinding.FragmentFavoritesBinding
import com.alvarengadev.movieschallenge.ui.adapter.FavoriteListAdapter
import com.alvarengadev.movieschallenge.ui.adapter.OnItemClickListener
import com.alvarengadev.movieschallenge.utils.remote.ResultUtils
import com.alvarengadev.movieschallenge.utils.gone
import com.alvarengadev.movieschallenge.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() = viewModel.apply {
        getMoviesFavoritesFromDatabase()
        statusGetMoviesFavoritesFromDatabase.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                ResultUtils.Status.SUCCESS -> {
                    val adapter = FavoriteListAdapter()
                    adapter.submitList(result.data)
                    adapter.setOnItemClickListener(object : OnItemClickListener {
                        override fun setOnItemClickListener(id: Int) {
                            val directions = FavoritesFragmentDirections.actionNavFavoritesToNavDetails(id)
                            findNavController().navigate(directions)
                        }
                    })
                    binding.rcyMoviesPopulars.apply {
                        this.adapter = adapter
                        visible()
                    }
                }
                ResultUtils.Status.ERROR -> {
                    with(binding) {
                        rcyMoviesPopulars.gone()
                        textError.apply {
                            text = result.message
                            visible()
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}