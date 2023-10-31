package com.elhady.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.enums.HomeItemType
import com.elhady.movies.domain.usecases.movieDetails.GetMovieDetailsUseCase
import com.elhady.movies.ui.adapter.ReviewInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.mappers.ReviewUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val actorUiMapper: ActorUiMapper,
    private val mediaUiMapper: MediaUiMapper,
    private val reviewUiMapper: ReviewUiMapper
) : BaseViewModel(), DetailsInteractionListener, ActorInteractionListener, MovieInteractionListener, ReviewInteractionListener {


    val args = MovieDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _detailsUiState = MutableStateFlow(DetailsUiState())
    val detailsUiState = _detailsUiState.asStateFlow()

    private val _detailsUiEvent = MutableStateFlow<Event<MovieDetailsUiEvent?>>(Event(null))
    val detailsUiEvent = _detailsUiEvent.asStateFlow()


    init {
        getData()
    }

    override fun getData() {
        _detailsUiState.update { it.copy(isLoading = true, errorUIStates = emptyList()) }
        getMovieDetails(args.movieID)
        getMovieCast(args.movieID)
        getSimilarMovies(args.movieID)
        getMovieReviews(args.movieID)
    }

    private fun getMovieDetails(movieId: Int) {
            viewModelScope.launch {
                val result = movieDetailsUiMapper.map(getMovieDetailsUseCase(movieId))
                _detailsUiState.update {
                    it.copy(movieDetailsResult = result, isLoading = false)
                }
                onAddMovieDetailsItemOfNestedView(DetailsItem.Header(_detailsUiState.value.movieDetailsResult))
            }
    }

    private fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            val result = getMovieDetailsUseCase.getMovieCast(movieId = movieId).map {
                actorUiMapper.map(it)
            }
            _detailsUiState.update {
                it.copy(movieCastResult = result, isLoading = false)
            }
            onAddMovieDetailsItemOfNestedView(DetailsItem.Cast(result))
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            val result = getMovieDetailsUseCase.getSimilarMovies(movieId = movieId).map {
                mediaUiMapper.map(it)
            }
            _detailsUiState.update {
                it.copy(similarMoviesResult = result, isLoading = false)
            }
            onAddMovieDetailsItemOfNestedView(DetailsItem.Similar(_detailsUiState.value.similarMoviesResult))
        }
    }

    private fun getMovieReviews(movieID: Int){
        viewModelScope.launch {
            val result = getMovieDetailsUseCase.getReview(movieId = movieID).reviews.map {
                reviewUiMapper.map(it)
            }
            _detailsUiState.update {
                it.copy(movieReviewsResult = result)
            }
            onAddMovieDetailsItemOfNestedView(DetailsItem.Reviews(_detailsUiState.value.movieReviewsResult))
        }
    }

    private fun onAddMovieDetailsItemOfNestedView(items: DetailsItem){
        val listItems = _detailsUiState.value.detailsItemsResult.toMutableList()
        listItems.add(items)
        _detailsUiState.update { it.copy(detailsItemsResult = listItems.toList()) }
    }

    override fun onClickBackButton() {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickBackButton)
        }
    }

    override fun onClickActor(actorID: Int) {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickCastEvent(castId = actorID))
        }
    }

    override fun onClickMovie(movieID: Int) {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickMovieEvent(movieID))
        }
    }

    override fun onClickSeeAllMovies(mediaType: HomeItemType) {
        TODO("Not yet implemented")
    }

    override fun onClickPlayTrailer() {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickPlayTrailerEvent)
        }
    }

    override fun onClickMedia(mediaId: Int) {
        TODO("Not yet implemented")
    }


}