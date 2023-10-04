package com.elhady.movies.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.home.GetPopularMoviesUseCase
import com.elhady.movies.ui.home.adapters.MovieInteractionListener
import com.elhady.movies.ui.home.homeUiState.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularUiMapper: PopularUiMapper
) :
    ViewModel(), MovieInteractionListener {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private fun getPopular() {
        viewModelScope.launch {
            try {
                getPopularMoviesUseCase().collect {items ->
                    if (items.isNotEmpty()) {
                       val popularUiState = items.map(popularUiMapper::map)
                        _homeUiState.update {
                            it.copy(popularMovie = HomeItem.Slider(popularUiState))
                        }
                    }
                }

            } catch (e: Exception) {
                Log.d("ViewModel", e.message.toString())
            }
        }
    }


    init {
        getPopular()
    }

    override fun onClickMovie(name: String) {
        TODO("Not yet implemented")
    }


}