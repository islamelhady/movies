package com.elhady.movies.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.movies.domain.usecases.search.GetSearchForMovieUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForMovieUseCase: GetSearchForMovieUseCase,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel(), MediaSearchInteractionListener {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    private val _searchUiEvent = MutableStateFlow<Event<SearchUiEvent>?>(null)
    val searchUiEvent = _searchUiEvent.asStateFlow()


    init {
//        getData()
    }

    override fun getData() {
        _searchUiEvent.update { Event(SearchUiEvent.ClickRetryEvent) }

    }

    private fun onSearchForMovies() {
        viewModelScope.launch {
            _searchUiState.update {
                it.copy(moviesSearchResult =  searchForMovieUseCase(it.inputSearch).map { pagingData ->
                    pagingData.map {
                        mediaUiMapper.map(it)
                    }
                })
            }
        }
    }

    fun onClickInputSearch(searchInput: CharSequence){
        _searchUiState.update { it.copy(inputSearch = searchInput.toString())}
        onSearchForMovies()

    }

    override fun onClickMediaResult() {
        TODO("Not yet implemented")
    }

    fun setError(combinedLoadStates: CombinedLoadStates){
        when(combinedLoadStates.refresh){
            is LoadState.Error -> _searchUiState.update { it.copy(isLoading = false, error = "") }
            LoadState.Loading -> _searchUiState.update { it.copy(isLoading = true, error = "") }
            is LoadState.NotLoading -> _searchUiState.update { it.copy(isLoading = false, error = "") }
        }


    }
}