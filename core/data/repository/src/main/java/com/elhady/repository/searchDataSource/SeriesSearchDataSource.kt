package com.elhady.repository.searchDataSource

import com.elhady.remote.response.series.SeriesDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject
import kotlin.properties.Delegates

class SeriesSearchDataSource @Inject constructor(private val service: MovieService) : BasePagingSource<SeriesDto>() {

    private var querySearch by Delegates.notNull<String>()
    fun setSearch(query: String) {
        querySearch = query
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesDto> {
        val pageNumber = params.key ?: 1
        val response = service.searchForSeries(query = querySearch, page = pageNumber)
        val responsePage = response.body()

        return try {
            LoadResult.Page(
                data = response.body()?.items ?: emptyList(),
                prevKey = null,
                nextKey = if (responsePage?.items?.isEmpty() == true) null else responsePage?.page?.plus(1)
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}