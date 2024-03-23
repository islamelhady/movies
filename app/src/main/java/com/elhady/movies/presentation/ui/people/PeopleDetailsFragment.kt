package com.elhady.movies.presentation.ui.people

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentPeopleDetailsBinding
import com.elhady.movies.presentation.ui.people.adapter.PeopleDetailsRecyclerAdapter
import com.elhady.movies.presentation.viewmodel.people.PeopleDetailsUiEvent
import com.elhady.movies.presentation.viewmodel.people.PeopleDetailsViewModel
import com.elhady.movies.presentation.viewmodel.people.PersonDetailsUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleDetailsFragment :
    BaseFragment<FragmentPeopleDetailsBinding, PersonDetailsUiState, PeopleDetailsUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_people_details
    override val viewModel: PeopleDetailsViewModel by viewModels()
    private lateinit var peopleMoviesAdapter: PeopleDetailsRecyclerAdapter
    private lateinit var peopleTvShowsAdapter: PeopleDetailsRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        getData()
    }

    private fun setAdapters() {
        peopleMoviesAdapter = PeopleDetailsRecyclerAdapter(mutableListOf(), viewModel)
        binding.recyclerViewPeopleMovies.adapter = peopleMoviesAdapter

        peopleTvShowsAdapter = PeopleDetailsRecyclerAdapter(mutableListOf(), viewModel)
        binding.recyclerViewPeopleTvShows.adapter = peopleTvShowsAdapter
    }

    private fun getData() {
        collectLatest {
            viewModel.state.collect { state ->
                peopleMoviesAdapter.setItems(state.movies)
                peopleTvShowsAdapter.setItems(state.tvShows)
                if (state.onErrors.isNotEmpty()) {
                    state.onErrors.last().let {
                        showSnackBar(it)
                    }
                }
            }
        }
    }

    override fun onEvent(event: PeopleDetailsUiEvent) {
        when (event) {
            PeopleDetailsUiEvent.BackNavigate -> findNavController().popBackStack()
            is PeopleDetailsUiEvent.ClickMovieEvent ->
                findNavController().navigate(
                    PeopleDetailsFragmentDirections.actionPeopleDetailsFragmentToMovieDetailsFragment(
                        event.itemId
                    )
                )

            is PeopleDetailsUiEvent.ClickTvShowsEvent ->
                findNavController().navigate(
                    PeopleDetailsFragmentDirections.actionPeopleDetailsFragmentToTvDetailsFragment(
                        event.itemId
                    )
                )
        }
    }

}