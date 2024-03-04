package com.elhady.viewmodel.home.homeUiState

import com.elhady.usecase.seeAllMedia.ShowMoreType
import kotlin.math.roundToInt

data class HomeUiState(
    val popularMovieSlider: List<PopularMoviesUiState> = emptyList(),
    val upcomingMovie: List<UpcomingMoviesUiState> = emptyList(),
    val trendingMovie: List<TrendingMoviesUiState> = emptyList(),
    val nowPlayingMovie: List<NowPlayingMoviesUiState> = emptyList(),
    val topRatedMovie: List<TopRatedMoviesUiState> = emptyList(),
    val onTheAirTVShows: List<OnTheAirTVShowsUiState> = emptyList(),
    val airingTodayTVShows: List<AiringTodayTVShowsUiState> = emptyList(),
    val tvShowsLists: List<TVShowsListsUiState> = emptyList(),
    val mysteryMovies: List<MysteryMoviesUiState> = emptyList(),
    val adventureMovies: List<AdventureMoviesUiState> = emptyList(),
    val popularPeople: List<PopularPeopleUiState> = emptyList(),
    val type: ShowMoreType = ShowMoreType.POPULAR_TV,
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)

data class PopularMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
    val genre: List<String>

) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class UpcomingMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
)

data class TrendingMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class NowPlayingMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val rate: Double
){
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class TopRatedMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class OnTheAirTVShowsUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class AiringTodayTVShowsUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class TVShowsListsUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class MysteryMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class AdventureMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class PopularPeopleUiState(
    val id: Int,
    val profilePath: String,
    val name: String
)
