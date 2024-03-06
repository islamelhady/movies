package com.elhady.remote.serviece

import com.elhady.remote.response.auth.RequestTokenResponse
import com.elhady.remote.response.auth.SessionResponse
import com.elhady.remote.response.DataWrapperResponse
import com.elhady.remote.ActorRemoteDto
import com.elhady.remote.TrendingTimeWindow
import com.elhady.remote.request.LoginRequest
import com.elhady.remote.response.AddListResponse
import com.elhady.remote.response.AddMovieDto
import com.elhady.remote.response.CreatedListDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.FavListDto
import com.elhady.remote.response.GenresWrapperResponse
import com.elhady.remote.response.RatedMovieDto
import com.elhady.remote.response.RatedSeriesDto
import com.elhady.remote.response.StatusResponse
import com.elhady.remote.response.StatusResponseDto
import com.elhady.remote.response.TrendingDto
import com.elhady.remote.response.profile.ProfileRemoteDto
import com.elhady.remote.response.actor.MovieCreditsDto
import com.elhady.remote.response.dto.MovieRemoteDto
import com.elhady.remote.response.genre.GenreMovieRemoteDto
import com.elhady.remote.response.movieDetails.MovieDetailsDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.dto.tvShowDetails.SeasonDto
import com.elhady.remote.response.dto.tvShowDetails.SeriesDetailsDto
import com.elhady.remote.response.dto.TVShowsRemoteDto
import com.elhady.remote.response.video.VideoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     *  MOVIE LISTS
     * * Popular
     * * Upcoming
     * * Top Rated
     * * Now Playing
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>


    /**
     *  Trending
     * * All
     * * Movies
     * * People
     */
    @GET("trending/all/{time_window}")
    suspend fun getTrending(@Path("time_window") timeWindow: String = TrendingTimeWindow.DAY.value): Response<DataWrapperResponse<TrendingDto>>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovie(@Path("time_window") timeWindow: String = TrendingTimeWindow.WEEK.value, @Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("trending/person/{time_window}")
    suspend fun getTrendingPerson(
        @Path("time_window") timeWindow: String = TrendingTimeWindow.DAY.value,
        @Query("page") page: Int = 1
    ): Response<DataWrapperResponse<ActorRemoteDto>>


    /**
     *  PEOPLE LISTS
     */
    @GET("person/popular")
    suspend fun getPopularPerson(@Query("page") page: Int = 1): Response<DataWrapperResponse<ActorRemoteDto>>

    @GET("person/{person_id}")
    suspend fun getPersonsDetails(@Path("person_id") actorID: Int): Response<ActorRemoteDto>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovies(@Path("person_id") actorID: Int): Response<MovieCreditsDto>

    /**
     *  GENRES
     * * Movies
     * * Series
     */
    @GET("genre/movie/list")
    suspend fun getListOfGenresForMovies(): Response<GenresWrapperResponse<GenreMovieRemoteDto>>

    @GET("genre/tv/list")
    suspend fun getGenreSeries(): Response<GenresWrapperResponse<GenreMovieRemoteDto>>

    /**
     * Movies
     * * Details
     * * Credits (Cast)
     * * Similar
     */
    @GET("movie/{movie_id}")
    suspend fun getDetailsMovies(@Path("movie_id") movieId: Int): Response<MovieDetailsDto>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(@Path("movie_id") movieId: Int): Response<CreditsDto>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(@Path("movie_id") movieId: Int): Response<DataWrapperResponse<MovieRemoteDto>>

    /**
     * TV SERIES
     * * Details
     * * Cast
     * * Similar
     * * Seasons
     */
    @GET("tv/{series_id}")
    suspend fun getSeriesDetails(@Path("series_id") seriesId: Int): Response<SeriesDetailsDto>

    @GET("tv/{series_id}/credits")
    suspend fun getSeriesCast(@Path("series_id") seriesId: Int): Response<CreditsDto>

    @GET("tv/{series_id}/similar")
    suspend fun getSimilarSeries(@Path("series_id") seriesId: Int): Response<DataWrapperResponse<TVShowsRemoteDto>>

    /**
     *  TV SEASONS
     * * Details
     */

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getSeasonDetails(@Path("series_id") seriesId: Int, @Path("season_number") seasonNumber: Int): Response<SeasonDto>

    /**
     *  Review
     * * movie
     * * series
     */
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(@Path("movie_id") movieId: Int): Response<DataWrapperResponse<ReviewDto>>

    @GET("tv/{series_id}/reviews")
    suspend fun getSeriesReview(@Path("series_id") seriesId: Int): Response<DataWrapperResponse<ReviewDto>>


    /**
     *   TV SERIES LISTS
     * * Airing Today
     * * On The Air
     * * Popular
     * * Top Rated
     */
    @GET("tv/airing_today")
    suspend fun getAiringTodayTV(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTV(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("tv/popular")
    suspend fun getPopularTV(@Query("page") page: Int =1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    /**
     *   DISCOVER
     * * All Movies
     * * Movies by Genre
     * * All Series
     * * Series TV by Genre
     */
    @GET("discover/movie")
    suspend fun getAllMovies(@Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>
    @GET("discover/movie")
    suspend fun getMoviesListByGenre(@Query("with_genres") genreID: Int, @Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("discover/tv")
    suspend fun getAllSeries(@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>
    @GET("discover/tv")
    suspend fun getSeriesByGenre(@Query("with_genres") genreID: Int ,@Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>


    /**
     * Search
     * * Movies
     * * Series
     * * Actors
     */
    @GET("search/movie")
    suspend fun searchForMovies(@Query("query") query: String, @Query("page") page: Int = 1): Response<DataWrapperResponse<MovieRemoteDto>>

    @GET("search/tv")
    suspend fun searchForSeries(@Query("query") query: String, @Query("page") page: Int = 1): Response<DataWrapperResponse<TVShowsRemoteDto>>

    @GET("search/person")
    suspend fun searchForActors(@Query("query") query: String, @Query("page") page: Int = 1): Response<DataWrapperResponse<ActorRemoteDto>>

    /**
     * Video
     * * Movie
     * * Series
     */
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(@Path("movie_id") movieId: Int): Response<VideoDto>

    @GET("tv/{tv_id}/videos")
    suspend fun getSeriesTrailer(@Path("tv_id") tvShowId: Int): Response<VideoDto>

    /**
     * Account
     * * Details
     * * Rated Movie
     * * Rated Series
     */

    @GET("account")
    suspend fun getAccountDetails(@Query("session_id") sessionId: String = " "): Response<ProfileRemoteDto>

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovie(@Path("account_id") accountId: Int = 0): Response<DataWrapperResponse<RatedMovieDto>>

    @FormUrlEncoded
    @POST("movie/{movie_id}/rating")
    suspend fun setRateMovie(@Path("movie_id") movieId: Int, @Field("value") rating: Float): Response<StatusResponse>

    @DELETE("movie/{movie_id}/rating")
    suspend fun deleteRatingMovie(@Path("movie_id") movieId: Int): Response<StatusResponse>

    @FormUrlEncoded
    @POST("tv/{tv_id}/rating")
    suspend fun setRatingSeries( @Path("tv_id") seriesId: Int, @Field("value") rating: Float): Response<StatusResponse>

    @DELETE("tv/{tv_id}/rating")
    suspend fun deleteRatingSeries(@Path("tv_id") seriesId: Int): Response<StatusResponse>

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTvShow(@Path("account_id") listId: Int = 0): Response<DataWrapperResponse<RatedSeriesDto>>

    /**
     * List
     * * Create
     * * Details
     * * Add Movie
     */
    @FormUrlEncoded
    @POST("list")
    suspend fun createList(
        @Query("session_id") sessionId: String,
        @Field("name") name: String,
        @Field("description") description: String = ""
    ): Response<AddListResponse>


    @GET("account/{account_id}/lists")
    suspend fun getCreatedList(
        @Path("account_id") accountId: Int = 0,
        @Query("session_id") sessionId: String
    ): Response<DataWrapperResponse<CreatedListDto>>

    @GET("list/{list_id}")
    suspend fun getList(@Path("list_id") listId: Int): Response<FavListDto>

    @FormUrlEncoded
    @POST("list/{list_id}/add_item")
    suspend fun addMovieToFavList(@Path("list_id") listId: Int, @Query("session_id") seriesId: String, @Field("media_id") movieId: Int): Response<AddMovieDto>

    @DELETE("list/{list_id}")
    suspend fun deleteList(@Path("list_id") listId: Int): Response<StatusResponseDto>


    /**
     *  AUTHENTICATION
     * * Create Request Token
     * * Create Session (with login)
     * * Create Session
     */
    @GET("authentication/token/new")
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<RequestTokenResponse>

    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSession(@Field("request_token") requestToken: String): Response<SessionResponse>

}


