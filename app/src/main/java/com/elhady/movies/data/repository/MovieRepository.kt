package com.elhady.movies.data.repository

import com.elhady.movies.data.local.database.entity.movies.AdventureMovieEntity
import com.elhady.movies.data.local.database.entity.movies.MysteryMovieEntity
import com.elhady.movies.data.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TrendingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>>

    suspend fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>>

    suspend fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>>

    suspend fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>>

    suspend fun getMysteryMovies(): Flow<List<MysteryMovieEntity>>

    suspend fun getAdventureMovies(): Flow<List<AdventureMovieEntity>>

    fun getTrendingPerson(): Flow<State<BaseResponse<PersonDto>>>

    suspend fun getTrendingMovie(): Flow<List<TrendingMovieEntity>>

    suspend fun getGenreMovies(): List<GenreDto>?

}