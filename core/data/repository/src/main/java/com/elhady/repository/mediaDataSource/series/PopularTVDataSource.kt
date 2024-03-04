package com.elhady.repository.mediaDataSource.series

import com.elhady.remote.response.dto.TVShowsRemoteDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject

class PopularTVDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<TVShowsRemoteDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShowsRemoteDto> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getPopularTV( page = pageNumber)
            LoadResult.Page(
                data = response.body()?.results ?: emptyList(),
                prevKey = null,
                nextKey = response.body()?.page?.plus(1)
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}