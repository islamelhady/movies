package com.elhady.movies.ui.movieDetails

import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState


sealed class DetailsItem(val priority: Int){
    data class Header(val data: MovieDetailsUiState): DetailsItem(0)
    data class Cast(val data: List<ActorUiState>): DetailsItem(1)
    data class Similar(val data: List<MediaUiState>): DetailsItem(2)
}