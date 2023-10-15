package com.elhady.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.HomeAdapter
import com.elhady.movies.ui.home.homeUiState.HomeUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homeUiState.collect { items ->
                homeAdapter.setItems(
                    mutableListOf(
                        items.popularMovie,
                        items.upcomingMovie,
                        items.trendingMovie,
                        items.nowPlayingMovie,
                        items.topRatedMovie,
                        items.onTheAirSeries,
                        items.airingTodaySeries,
                        items.tvSeriesLists,
                        items.mysteryMovies,
                        items.adventureMovies,
                        items.actors
                    )
                )
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homeUiEvent.collect{
                it.getContentIfNotHandled()?.let {
                    onEventClick(it)
                }
            }
        }

    }

    fun onEventClick(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                        event.movieID
                    )
                )
            }
        }
    }



    private fun setupAdapter() {
        homeAdapter = HomeAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = homeAdapter
    }
}