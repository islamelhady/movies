package com.elhady.usecase.home.series

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.SeriesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOnTheAirSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val onTheAirSeriesMapper: OnTheAirSeriesMapper
) {
    suspend operator fun invoke(): List<MovieEntity>{
        return seriesRepository.getOnTheAirSeries().map(onTheAirSeriesMapper::map)
    }
}