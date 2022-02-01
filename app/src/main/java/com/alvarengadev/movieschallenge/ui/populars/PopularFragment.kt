package com.alvarengadev.movieschallenge.ui.populars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.alvarengadev.movieschallenge.databinding.FragmentPopularBinding
import com.alvarengadev.movieschallenge.ui.adapter.MovieListAdapter
import com.alvarengadev.movieschallenge.ui.adapter.OnItemClickListener
import com.alvarengadev.movieschallenge.utils.gone
import com.alvarengadev.movieschallenge.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        val adapter = MovieListAdapter()

        getMoviesFromServer().observe(viewLifecycleOwner) { paging ->
            adapter.submitData(lifecycle, paging)
            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun setOnItemClickListener(id: Int) {
                    val directions = PopularFragmentDirections.actionNavPopularToDetailsFragment(id)
                    findNavController().navigate(directions)
                }
            })
            binding.rcyMoviesPopulars.adapter = adapter
        }

        adapter.addLoadStateListener { status ->
            when (status.refresh) {
                is LoadState.Loading -> {
                    showList(false)
                }
                is LoadState.NotLoading -> {
                    showList(true)
                }
                is LoadState.Error -> {
                    //binding.textError.text =
                }
            }
        }
    }

    private fun showList(
        isShow: Boolean
    ) = with(binding) {
        if (isShow) {
            progressBar.gone()
            rcyMoviesPopulars.visible()
        } else {
            progressBar.visible()
            rcyMoviesPopulars.gone()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}