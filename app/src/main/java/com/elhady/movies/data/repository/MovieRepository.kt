package com.elhady.movies.data.repository

import com.elhady.movies.data.local.database.entity.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>>

    suspend fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>>

    suspend fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>>

    suspend fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>>

    fun getTrendingPerson(): Flow<State<BaseResponse<PersonDto>>>

    suspend fun getTrendingMovie(): Flow<List<TrendingMovieEntity>>

    suspend fun getGenreMovies(): List<GenreDto>?

}