package com.elhady.movies.ui.profile.watchHistory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentWatchHistoryBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchHistoryFragment : BaseFragment<FragmentWatchHistoryBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_watch_history
    override val viewModel: WatchHistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        collectEvent()
    }

    private fun setAdapter() {
        binding.recyclerWatchHistory.adapter = WatchHistoryAdapter(mutableListOf(), viewModel)
    }

    private fun collectEvent() {
        collectLast(viewModel.uiEvent) { event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: WatchHistoryUiEvent) {
        val action = when (event) {
            is WatchHistoryUiEvent.MovieEvent -> WatchHistoryFragmentDirections.actionWatchHistoryFragmentToMovieDetailsFragment(
                event.movieId
            )

            is WatchHistoryUiEvent.SeriesEvent -> WatchHistoryFragmentDirections.actionWatchHistoryFragmentToTvShowDetailsFragment(
                event.tvShowId
            )
        }
        findNavController().navigate(action)

    }


}