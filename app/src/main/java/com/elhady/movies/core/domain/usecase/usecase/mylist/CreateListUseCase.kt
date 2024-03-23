package com.elhady.movies.core.domain.usecase.usecase.mylist

import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(nameList:String): Boolean {
        return movieRepository.addList(name = nameList)
    }
}