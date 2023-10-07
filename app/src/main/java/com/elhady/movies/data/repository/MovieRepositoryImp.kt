package com.elhady.movies.data.repository

import com.elhady.movies.data.Constants
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.entity.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.local.mappers.TrendingMovieMapper
import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.local.mappers.NowPlayingMovieMapper
import com.elhady.movies.data.local.mappers.UpcomingMovieMapper
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.local.mappers.PopularMovieMapper
import com.elhady.movies.data.local.mappers.TopRatedMovieMapper
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val popularMovieMapper: PopularMovieMapper,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration,
    private val trendingMovieMapper: TrendingMovieMapper,
    private val upcomingMovieMapper: UpcomingMovieMapper,
    private val nowPlayingMovieMapper: NowPlayingMovieMapper,
    private val topRatedMovieMapper: TopRatedMovieMapper
) :
    MovieRepository, BaseRepository() {

    /**
     *  Popular Movies
     */
    override suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.POPULAR_MOVIE_REQUEST_DATE_KEY),
            ::refreshPopularMovies
        )
        return movieDao.getPopularMovies()
    }

    private suspend fun refreshPopularMovies(currentDate: Date) {
        val genre = getGenreMovies() ?: emptyList()
        wrap(
            { movieService.getPopularMovies() },
            { items ->
                items?.map {
                    popularMovieMapper.map(it, genre)
                }
            },
            {
                movieDao.insertPopularMovie(it)
                appConfiguration.saveRequestDate(
                    Constants.POPULAR_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  Upcoming Movies
     */

    override suspend fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.UPCOMING_MOVIE_REQUEST_DATE_KEY),
            ::refreshUpcomingMovies
        )
        return movieDao.getUpcomingMovies()
    }

    suspend fun refreshUpcomingMovies(currentDate: Date) {
        wrap({
            movieService.getUpcomingMovies()
        }, { items ->
            items?.map {
                upcomingMovieMapper.map(it)
            }
        }, {
            movieDao.deleteUpcomingMovies()
            movieDao.insertUpcomingMovie(it)
            appConfiguration.saveRequestDate(
                Constants.UPCOMING_MOVIE_REQUEST_DATE_KEY,
                currentDate.time
            )
        })
    }

    /**
     *  Top Rated Movies
     */
    override suspend fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>> {
        refreshOneTimePerDay(appConfiguration.getRequestDate(Constants.TOP_RATED_MOVIE_REQUEST_DATE_KEY), ::refreshTopRatedMovies)
        return movieDao.getTopRatedMovies()
    }

    suspend fun refreshTopRatedMovies(currentDate: Date){
        wrap(
            {movieService.getTopRatedMovies()},
            { items ->
                items?.map { topRatedMovieMapper.map(it) }
            },
            {
                movieDao.deleteTopRatedMovies()
                movieDao.insertTopRatedMovies(it)
                appConfiguration.saveRequestDate(Constants.TOP_RATED_MOVIE_REQUEST_DATE_KEY, currentDate.time)
            }
        )
    }

    /**
     *  Now Playing Movies
     */
    override suspend fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.NOW_PLAYING_MOVIE_REQUEST_DATE_KEY),
            ::refreshNowPlayingMovies
        )
        return movieDao.getNowPlayingMovies()
    }

    private suspend fun refreshNowPlayingMovies(currentDate: Date) {
        wrap(
            { movieService.getNowPlayingMovies() },
            { items ->
                items?.map { nowPlayingMovieMapper.map(it) }
            },
            {
                movieDao.deleteNowPlayingMovies()
                movieDao.insertNowPlayingMovies(it)
                appConfiguration.saveRequestDate(
                    Constants.NOW_PLAYING_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    override fun getTrendingPerson(): Flow<State<BaseResponse<PersonDto>>> {
        return wrapWithFlow { movieService.getTrendingPerson() }
    }

    /**
     *  Trending Movies
     */
    override suspend fun getTrendingMovie(): Flow<List<TrendingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.TRENDING_MOVIE_REQUEST_DATE_KEY),
            ::refreshTrendingMovies
        )
        return movieDao.getAllTrendingMovies()
    }

    suspend fun refreshTrendingMovies(currentDate: Date) {
        wrap(
            {
                movieService.getTrendingMovie()
            },
            { items ->
                items?.map {
                    trendingMovieMapper.map(it)
                }
            },
            {
                movieDao.deleteTrendingMovies()
                movieDao.insertTrendingMovies(it)
                appConfiguration.saveRequestDate(
                    Constants.TRENDING_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  Genre Movies
     */
    override suspend fun getGenreMovies(): List<GenreDto>? {
        return movieService.getGenreMovies().body()?.genres
    }

}